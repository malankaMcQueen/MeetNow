package com.example.meetnow.repository.mapper;

import com.example.meetnow.service.model.Interest;
import org.jdbi.v3.core.result.RowReducer;
import org.jdbi.v3.core.result.RowView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static com.example.meetnow.repository.mapper.ColumnNames.*;

public class InterestMapReducer implements RowReducer<Map<Long, List<Interest>>, List<Interest>> {

    private static final String INTEREST_NAME = "interest_name";
    private static final String CATEGORY_ID = "category_id";

    @Override
    public Map<Long, List<Interest>> container() {
        return new HashMap<>();
    }

    @Override
    public void accumulate(Map<Long, List<Interest>> container, RowView rowView) {
        Long eventId = rowView.getColumn(EVENT_ID, Long.class);
        Interest interest = extractInterest(rowView);
        List<Interest> interestList = container.computeIfAbsent(eventId, k -> new ArrayList<>());

        interestList.add(interest);
    }

    private static Interest extractInterest(RowView rowView) {
        Long interestId = rowView.getColumn(INTEREST_ID, Long.class);
        String interestName = rowView.getColumn(INTEREST_NAME, String.class);
        Long categoryId = rowView.getColumn(CATEGORY_ID, Long.class);

        return new Interest(interestId, interestName, categoryId);
    }

    @Override
    public Stream<List<Interest>> stream(Map<Long, List<Interest>> container) {
        return container.values().stream();
    }
}

