package io.vinta.containerbase.core.inout.vintademo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vinta.containerbase.common.enums.ContainerState;
import io.vinta.containerbase.common.enums.TransportEquipmentType;
import io.vinta.containerbase.core.fileform.entities.FileFormSchema;
import io.vinta.containerbase.core.inout.FileFormImportRowValidator;
import io.vinta.containerbase.core.inout.models.RowValidationResult;
import io.vinta.containerbase.core.inout.vintademo.enums.VintaDemoDataModelSchema;
import io.vinta.containerbase.core.inout.vintademo.models.VintaDemoContainerImportModelData;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContainerVintaDemoCsvRowDataValidator implements FileFormImportRowValidator {
	private final ObjectMapper mapper;
	private final Validator validator;

	private static final String ERROR_CONCAT_PATTERN = "%s; %s";

	@Override
	public RowValidationResult validate(FileFormSchema csvSchema, String[] rowData) {
		final var containerDataModel = VintaDemoContainerImportModelData.builder()

				.containerNumber(getColumnDataByKey(csvSchema, rowData, VintaDemoDataModelSchema.CONTAINER_NUMBER
						.getHeaderKey()).orElse(null))
				.isoEquipmentCode(getColumnDataByKey(csvSchema, rowData, VintaDemoDataModelSchema.ISO_EQUIPMENT_CODE
						.getHeaderKey()).orElse(null))
				.equipmentReference(getColumnDataByKey(csvSchema, rowData, VintaDemoDataModelSchema.EQUIPMENT_REFERENCE
						.getHeaderKey()).orElse(null))
				.transportEquipmentType(getColumnDataByKey(csvSchema, rowData,
						VintaDemoDataModelSchema.TRANSPORT_EQUIPMENT_TYPE.getHeaderKey()).map(
								TransportEquipmentType::valueOf)
						.orElse(null)

				)
				.tareWeightKg(getColumnDataByKey(csvSchema, rowData, VintaDemoDataModelSchema.TARE_WEIGHT_KG
						.getHeaderKey()).map(Integer::valueOf)
						.orElse(null))
				.maxGrossWeightKg(getColumnDataByKey(csvSchema, rowData, VintaDemoDataModelSchema.MAX_GROSS_WEIGHT_KG
						.getHeaderKey()).map(Integer::valueOf)
						.orElse(null))
				.payloadWeightKg(getColumnDataByKey(csvSchema, rowData, VintaDemoDataModelSchema.PAYLOAD_WEIGHT_KG
						.getHeaderKey()).map(Integer::valueOf)
						.orElse(null))
				.state(getColumnDataByKey(csvSchema, rowData, VintaDemoDataModelSchema.STATE.getHeaderKey()).map(
						ContainerState::valueOf)
						.orElse(null))
				.damageDescription(getColumnDataByKey(csvSchema, rowData, VintaDemoDataModelSchema.DAMAGE_DESCRIPTION
						.getHeaderKey()).orElse(null))
				.bookingReference(getColumnDataByKey(csvSchema, rowData, VintaDemoDataModelSchema.BOOKING_REFERENCE
						.getHeaderKey()).orElse(null))
				.shipmentReference(getColumnDataByKey(csvSchema, rowData, VintaDemoDataModelSchema.SHIPMENT_REFERENCE
						.getHeaderKey()).orElse(null))
				.contentsDescription(getColumnDataByKey(csvSchema, rowData,
						VintaDemoDataModelSchema.CONTENTS_DESCRIPTION.getHeaderKey()).orElse(null))
				.sealNumber(getColumnDataByKey(csvSchema, rowData, VintaDemoDataModelSchema.SEAL_NUMBER.getHeaderKey())
						.orElse(null))
				.sealSource(getColumnDataByKey(csvSchema, rowData, VintaDemoDataModelSchema.SEAL_SOURCE.getHeaderKey())
						.orElse(null))
				.ownerShippingLineCode(getColumnDataByKey(csvSchema, rowData,
						VintaDemoDataModelSchema.OWNER_SHIPPING_LINE_CODE.getHeaderKey()).orElse(null))
				.ownerShippingSCAC(getColumnDataByKey(csvSchema, rowData, VintaDemoDataModelSchema.OWNER_SHIPPING_SCAC
						.getHeaderKey()).orElse(null))
				.ownerName(getColumnDataByKey(csvSchema, rowData, VintaDemoDataModelSchema.OWNER_NAME.getHeaderKey())
						.orElse(null))
				.ownerAddress(getColumnDataByKey(csvSchema, rowData, VintaDemoDataModelSchema.OWNER_ADDRESS
						.getHeaderKey()).orElse(null))
				.build();

		final var results = validator.validate(containerDataModel);

		var errorMessage = getErrorMessage(results);
		return RowValidationResult.builder()
				.isValid(StringUtils.isBlank(errorMessage))
				.errorMessage(StringUtils.isNotBlank(errorMessage) ? errorMessage : null)
				.modelData(mapper.convertValue(containerDataModel, new TypeReference<>() {
				}))
				.build();
	}
}
