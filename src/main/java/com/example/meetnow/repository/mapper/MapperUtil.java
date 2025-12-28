package com.example.meetnow.repository.mapper;

import com.example.meetnow.service.model.Interest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jdbi.v3.core.result.RowView;

import java.util.Optional;

import static com.example.meetnow.repository.mapper.ColumnNames.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapperUtil {

    static Optional<Interest> extractInterest(RowView rowView) {
        Long interestId = rowView.getColumn(INTEREST_ID, Long.class);
        if (interestId == null) {
            return Optional.empty();
        }
        String interestName = rowView.getColumn(INTEREST_NAME, String.class);
        Long categoryId = rowView.getColumn(CATEGORY_ID, Long.class);

        return Optional.of(new Interest(interestId, interestName, categoryId));
    }

}
