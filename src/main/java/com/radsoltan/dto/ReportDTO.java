package com.radsoltan.dto;

/**
 * Immutable class that represents emergency report.
 *
 * @param info includes all the information required for the report
 * @param suggestion includes suggestions if 3wa address was not recognized
 */
public record ReportDTO(ReportInfoDTO info, ReportSuggestionDTO suggestion) {
}
