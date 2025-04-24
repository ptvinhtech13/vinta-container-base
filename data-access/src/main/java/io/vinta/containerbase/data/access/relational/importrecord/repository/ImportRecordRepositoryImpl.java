package io.vinta.containerbase.data.access.relational.importrecord.repository;

import io.vinta.containerbase.common.baseid.ImportJobId;
import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.common.querydsl.WhereBuilder;
import io.vinta.containerbase.core.importrecord.ImportRecordRepository;
import io.vinta.containerbase.core.importrecord.entities.ImportRecord;
import io.vinta.containerbase.core.importrecord.request.FilterImportRecord;
import io.vinta.containerbase.core.importrecord.request.FindImportRecordQuery;
import io.vinta.containerbase.data.access.relational.importrecord.entities.ImportRecordEntity;
import io.vinta.containerbase.data.access.relational.importrecord.mapper.ImportRecordEntityMapper;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImportRecordRepositoryImpl implements ImportRecordRepository {

	private final JpaImportRecordRepository repository;

	@Override
	public void deleteAllByImportJobId(ImportJobId jobId) {
		repository.deleteAllByImportJobId(jobId.getValue());
	}

	@Override
	public List<ImportRecord> saveAll(List<ImportRecord> records) {
		final var existingIds = records.stream()
				.filter(it -> it.getId() != null)
				.map(it -> it.getId()
						.getValue())
				.collect(Collectors.toSet());
		final var existingMaps = existingIds.isEmpty()
				? Map.<Long, ImportRecordEntity>of()
				: repository.findAllById(existingIds)
						.stream()
						.collect(Collectors.toMap(ImportRecordEntity::getId, Function.identity()));

		final var existingUpdatedEntities = records.stream()
				.filter(it -> existingMaps.containsKey(it.getId()
						.getValue()))
				.map(record -> {
					final var existing = existingMaps.get(record.getId()
							.getValue());
					return ImportRecordEntityMapper.INSTANCE.toUpdateEntity(existing, record);
				})
				.toList();

		final var newEntities = records.stream()
				.filter(it -> !existingMaps.containsKey(it.getId()
						.getValue()))
				.map(ImportRecordEntityMapper.INSTANCE::toNewEntity)
				.toList();

		return repository.saveAll(Stream.concat(existingUpdatedEntities.stream(), newEntities.stream())
				.toList())
				.stream()
				.map(ImportRecordEntityMapper.INSTANCE::toModel)
				.toList();

	}

	@Override
	public Paging<ImportRecord> queryImportRecords(FindImportRecordQuery query) {
		final var filter = Optional.ofNullable(query.getFilter())
				.orElseGet(() -> FilterImportRecord.builder()
						.build());

		final var pageable = PageRequest.of(query.getPage(), query.getSize(), Sort.Direction.valueOf(query
				.getSortDirection()), query.getSortFields()
						.toArray(String[]::new));

		final var predicate = WhereBuilder.build();
		//				.applyIf(filter.getByCreatedFrom() != null, where -> where.and(
		//						QExportJobEntity.exportJobEntity.createdAt.after(filter.getByCreatedFrom())))
		//				.applyIf(filter.getByCreatedTo() != null, where -> where.and(QExportJobEntity.exportJobEntity.createdAt
		//						.after(filter.getByCreatedTo())))
		//				.applyIf(CollectionUtils.isNotEmpty(filter.getByStatuses()), where -> where.and(
		//						QExportJobEntity.exportJobEntity.status.in(filter.getByStatuses())));

		final var pageResult = repository.findAllWithBase(predicate, pageable);

		return new Paging<>(pageResult.getContent()
				.stream()
				.map(ImportRecordEntityMapper.INSTANCE::toModel)
				.toList(), pageResult.getTotalElements(), pageResult.getTotalPages(), pageResult.getPageable()
						.getPageNumber(), pageResult.getSort()
								.stream()
								.map(Sort.Order::toString)
								.toList());

	}
}
