package io.vinta.containerbase.data.access.relational.importjob.repository;

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.common.querydsl.WhereBuilder;
import io.vinta.containerbase.core.importjob.ImportJobRepository;
import io.vinta.containerbase.core.importjob.entities.ImportJob;
import io.vinta.containerbase.core.importjob.request.FilterImportJob;
import io.vinta.containerbase.core.importjob.request.FindImportJobQuery;
import io.vinta.containerbase.data.access.relational.importjob.entities.QImportJobEntity;
import io.vinta.containerbase.data.access.relational.importjob.mapper.ImportJobEntityMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImportJobRepositoryImpl implements ImportJobRepository {

	private final JpaImportJobRepository repository;

	@Override
	public ImportJob save(ImportJob job) {
		return ImportJobEntityMapper.INSTANCE.toModel(Optional.ofNullable(job.getId())
				.map(jobId -> {
					final var existing = repository.findById(jobId.getValue())
							.orElseGet(() -> repository.save(ImportJobEntityMapper.INSTANCE.toNewEntity(job)));
					return repository.save(ImportJobEntityMapper.INSTANCE.toUpdateEntity(existing, job));
				})
				.orElseGet(() -> repository.save(ImportJobEntityMapper.INSTANCE.toNewEntity(job))));
	}

	@Override
	public Paging<ImportJob> queryImportJobs(FindImportJobQuery query) {

		final var filter = Optional.ofNullable(query.getFilter())
				.orElseGet(() -> FilterImportJob.builder()
						.build());

		final var pageable = PageRequest.of(query.getPage(), query.getSize(), Sort.Direction.valueOf(query
				.getSortDirection()), query.getSortFields()
						.toArray(String[]::new));

		final var predicate = WhereBuilder.build()
				.applyIf(CollectionUtils.isNotEmpty(filter.getByStatuses()), where -> where.and(
						QImportJobEntity.importJobEntity.status.in(filter.getByStatuses())))
				.applyIf(filter.getByCreatedFrom() != null, where -> where.and(
						QImportJobEntity.importJobEntity.createdAt.after(filter.getByCreatedFrom())))
				.applyIf(filter.getByCreatedTo() != null, where -> where.and(QImportJobEntity.importJobEntity.createdAt
						.after(filter.getByCreatedTo())));

		final var pageResult = repository.findAllWithBase(predicate, pageable);

		return new Paging<>(pageResult.getContent()
				.stream()
				.map(ImportJobEntityMapper.INSTANCE::toModel)
				.toList(), pageResult.getTotalElements(), pageResult.getTotalPages(), pageResult.getPageable()
						.getPageNumber(), pageResult.getSort()
								.stream()
								.map(Sort.Order::toString)
								.toList());

	}
}
