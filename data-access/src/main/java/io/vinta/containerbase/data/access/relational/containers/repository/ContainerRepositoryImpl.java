package io.vinta.containerbase.data.access.relational.containers.repository;

import io.vinta.containerbase.common.paging.Paging;
import io.vinta.containerbase.common.querydsl.WhereBuilder;
import io.vinta.containerbase.core.containers.ContainerRepository;
import io.vinta.containerbase.core.containers.entities.Container;
import io.vinta.containerbase.core.containers.request.FilterContainer;
import io.vinta.containerbase.core.containers.request.FindContainerQuery;
import io.vinta.containerbase.data.access.relational.containers.entities.QContainerEntity;
import io.vinta.containerbase.data.access.relational.containers.mapper.ContainerEntityMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@RequiredArgsConstructor
public class ContainerRepositoryImpl implements ContainerRepository {

	private final JpaContainerRepository repository;

	@Override
	public Paging<Container> queryContainers(FindContainerQuery query) {
		final var filter = Optional.ofNullable(query.getFilter())
				.orElseGet(() -> FilterContainer.builder()
						.build());

		final var pageable = PageRequest.of(query.getPage(), query.getSize(), Sort.Direction.valueOf(query
				.getSortDirection()), query.getSortFields()
						.toArray(String[]::new));

		final var predicate = WhereBuilder.build()
				.applyIf(StringUtils.isNotBlank(filter.getByImportJobId()), where -> where.and(
						QContainerEntity.containerEntity.importJobId.eq(filter.getByImportJobId())))
				.applyIf(!CollectionUtils.isEmpty(filter.getByContainerNumbers()), where -> where.and(
						QContainerEntity.containerEntity.containerNumber.in(filter.getByContainerNumbers())))
				.applyIf(StringUtils.isNotBlank(filter.getByBookingReference()), where -> where.and(
						QContainerEntity.containerEntity.bookingReference.eq(filter.getByBookingReference())))
				.applyIf(StringUtils.isNotBlank(filter.getByOwnerShippingLineCode()), where -> where.and(
						QContainerEntity.containerEntity.ownerShippingLineCode.eq(filter.getByOwnerShippingLineCode())))
				.applyIf(filter.getByCreatedFrom() != null, where -> where.and(
						QContainerEntity.containerEntity.createdAt.after(filter.getByCreatedFrom())))
				.applyIf(filter.getByCreatedTo() != null, where -> where.and(QContainerEntity.containerEntity.createdAt
						.before(filter.getByCreatedTo())));

		final var pageResult = repository.findAllWithBase(predicate, pageable);

		return new Paging<>(pageResult.getContent()
				.stream()
				.map(ContainerEntityMapper.INSTANCE::toModel)
				.toList(), pageResult.getTotalElements(), pageResult.getTotalPages(), pageResult.getPageable()
						.getPageNumber(), pageResult.getSort()
								.stream()
								.map(Sort.Order::toString)
								.toList());

	}

	@Override
	public List<Container> saveAll(List<Container> containers) {
		return repository.saveAll(containers.stream()
				.map(ContainerEntityMapper.INSTANCE::toEntity)
				.toList())
				.stream()
				.map(ContainerEntityMapper.INSTANCE::toModel)
				.toList();

	}
}
