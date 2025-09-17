package com.shoplegacy.order.service;

import com.shoplegacy.order.mapper.OrderSequenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrderNumberGeneratorService {

        @Autowired
    OrderSequenceMapper orderSequenceMapper;

    @Transactional
    public String generateOrderNumber() {

        try {
            LocalDate today = LocalDate.now();
            Date seqDate = java.sql.Date.valueOf(today);

            Integer currentSeq = orderSequenceMapper.selectForUpdate(seqDate);

            if (currentSeq == null) {
                orderSequenceMapper.insertSequence(seqDate, 1);
                currentSeq = 1;
            } else {
                orderSequenceMapper.updateSequence(seqDate);
                currentSeq += 1;
            }

            // 주문번호 : ORD20250606-0001
            String formattedSeq = String.format("%04d", currentSeq);

            return "ORD" + today.format(DateTimeFormatter.BASIC_ISO_DATE) + "-" + formattedSeq;

        } catch(Exception e) {
            System.out.println("Failed To Make Order No : " + e.getMessage());
            throw new RuntimeException(e);
        }

    }

}
