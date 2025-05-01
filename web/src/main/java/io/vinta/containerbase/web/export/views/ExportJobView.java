package io.vinta.containerbase.web.export.views;

import io.vinta.containerbase.common.enums.ExportJobStatus;
import io.vinta.containerbase.core.containers.request.FilterContainer;
import io.vinta.containerbase.web.fileform.views.FileFormView;
import java.time.Instant;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class ExportJobView {
	private final Long id;
	private final FileFormView exportForm;
	private final ExportJobStatus status;
	private final Long totalContainer;
	private final String fileOutputPath;
	private final Long totalExportedContainer;
	private final FilterContainer filterContainer;
	private final String remark;
	private final Instant createdAt;
	private final Instant updatedAt;

	public String getFilterConditions() {
		var conditions = "";
		if (filterContainer == null) {
			return conditions;
		}
		if (filterContainer.getByImportJobId() != null) {
			conditions += "JobID: " + filterContainer.getByImportJobId() + ".";
		}
		if (filterContainer.getByContainerNumbers() != null) {
			conditions += "Container Numbers: " + filterContainer.getByContainerNumbers() + ".";
		}
		if (filterContainer.getByBookingReference() != null) {
			conditions += "Booking Reference: " + filterContainer.getByBookingReference() + ".";
		}
		if (filterContainer.getByOwnerShippingLineCode() != null) {
			conditions += "Owner Shipping Line: " + filterContainer.getByOwnerShippingLineCode() + ".";
		}
		if (filterContainer.getByCreatedFrom() != null) {
			conditions += "Date From: " + filterContainer.getByCreatedFrom() + ".";
		}
		if (filterContainer.getByCreatedTo() != null) {
			conditions += "Date To: " + filterContainer.getByCreatedTo() + ".";
		}
		return conditions;
	}
}
