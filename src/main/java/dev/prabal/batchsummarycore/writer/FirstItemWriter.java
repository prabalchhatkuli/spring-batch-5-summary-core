package dev.prabal.batchsummarycore.writer;

import dev.prabal.batchsummarycore.model.StudentCsv;
import dev.prabal.batchsummarycore.model.StudentJson;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class FirstItemWriter implements ItemWriter<StudentJson> {
    @Override
    public void write(Chunk<? extends StudentJson> chunk) throws Exception {
        System.out.println("Inside Item Writer");
        chunk.getItems().stream().forEach(System.out::println);
    }
}
