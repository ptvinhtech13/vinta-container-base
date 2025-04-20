package io.vinta.containerbase.data.access.relational.exportjob.repository;

import io.vinta.containerbase.common.baseid.ExportJobId;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.common.querydsl.WhereBuilder;
import io.vinta.containerbase.core.export.ExportJobRepository;
import io.vinta.containerbase.core.export.entities.ExportJob;
import io.vinta.containerbase.core.export.request.FilterExportJob;
import io.vinta.containerbase.core.export.request.FindExportJobQuery;
import io.vinta.containerbase.core.fileform.FileFormManagerService;
import io.vinta.containerbase.data.access.relational.exportjob.entities.QExportJobEntity;
import io.vinta.containerbase.data.access.relational.exportjob.mapper.ExportJobEntityMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExportJobRepositoryImpl implements ExportJobRepository {

	private final JpaExportJobRepository exportJobRepository;

	private final FileFormManagerService fileFormManagerService;

	@Override
	public Paging<ExportJob> queryExportJobs(FindExportJobQuery query) {
		final var filter = Optional.ofNullable(query.getFilter())
				.orElseGet(() -> FilterExportJob.builder()
						.build());

		final var pageable = PageRequest.of(query.getPage(), query.getSize(), Sort.Direction.valueOf(query
				.getSortDirection()), query.getSortFields()
						.toArray(String[]::new));

		final var predicate = WhereBuilder.build()
				.applyIf(filter.getByCreatedFrom() != null, where -> where.and(
						QExportJobEntity.exportJobEntity.createdAt.after(filter.getByCreatedFrom())))
				.applyIf(filter.getByCreatedTo() != null, where -> where.and(QExportJobEntity.exportJobEntity.createdAt
						.after(filter.getByCreatedTo())))
				.applyIf(CollectionUtils.isNotEmpty(filter.getByStatuses()), where -> where.and(
						QExportJobEntity.exportJobEntity.status.in(filter.getByStatuses())));

		final var pageResult = exportJobRepository.findAllWithBase(predicate, pageable);

		return new Paging<>(pageResult.getContent()
				.stream()
				.map(it -> ExportJobEntityMapper.INSTANCE.toModel(fileFormManagerService, it))
				.toList(), pageResult.getTotalElements(), pageResult.getTotalPages(), pageResult.getPageable()
						.getPageNumber(), pageResult.getSort()
								.stream()
								.map(Sort.Order::toString)
								.toList());

	}

	@Override
	public ExportJob save(ExportJob job) {
		return Optional.ofNullable(job.getId())
				.map(it -> {
					final var existing = exportJobRepository.findById(it.getValue())
							.orElseThrow();
					return exportJobRepository.save(ExportJobEntityMapper.INSTANCE.toUpdateEntity(existing, job));
				})
				.map(it -> ExportJobEntityMapper.INSTANCE.toModel(fileFormManagerService, it))
				.orElseGet(() -> ExportJobEntityMapper.INSTANCE.toModel(fileFormManagerService, exportJobRepository
						.save(ExportJobEntityMapper.INSTANCE.toNewEntity(job))));
	}

	@Override
	public Optional<ExportJob> findOneByExportJobId(ExportJobId exportJobId) {
		if (exportJobId == null || exportJobId.getValue() == null) {
			throw new IllegalArgumentException("ExportJobId cannot be null");
		}

		return exportJobRepository.findById(exportJobId.getValue())
				.map(it -> ExportJobEntityMapper.INSTANCE.toModel(fileFormManagerService, it));
	}
}
