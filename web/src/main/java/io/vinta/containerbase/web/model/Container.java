package io.vinta.containerbase.web.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Container {
	private String name;
	private String containerId;
	private LocalDateTime createdAt;
}