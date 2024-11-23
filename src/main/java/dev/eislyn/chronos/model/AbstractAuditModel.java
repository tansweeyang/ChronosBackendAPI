package dev.eislyn.chronos.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AbstractAuditModel {
    private Long createdBy;

    private LocalDate createdDate;

    private Long modifiedBy;

    private LocalDate modifiedDate;
}
