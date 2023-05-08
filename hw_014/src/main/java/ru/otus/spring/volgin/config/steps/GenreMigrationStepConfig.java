package ru.otus.spring.volgin.config.steps;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.volgin.domain.document.GenreDoc;
import ru.otus.spring.volgin.domain.entity.Genre;
import ru.otus.spring.volgin.mapper.EntityMapper;

import javax.persistence.EntityManagerFactory;

/**
 * Шаг миграции жанров
 */
@Configuration
@RequiredArgsConstructor
public class GenreMigrationStepConfig {
    private final EntityManagerFactory managerFactory;
    private final MongoTemplate mongoTemplate;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityMapper mapper;

    @StepScope
    @Bean
    public JpaPagingItemReader<Genre> genreReader() {
        return new JpaPagingItemReaderBuilder<Genre>()
                .name("genreReader")
                .entityManagerFactory(managerFactory)
                .queryString("select g from Genre g")
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Genre, GenreDoc> genreProcessor() {
        return mapper::toGenreDocument;
    }

    @StepScope
    @Bean
    public MongoItemWriter<GenreDoc> genreWriter() {
        return new MongoItemWriterBuilder<GenreDoc>()
                .collection("genres")
                .template(mongoTemplate)
                .build();
    }

    @Bean
    public Step genreMigrationStep(JpaPagingItemReader<Genre> genreReader,
                                   ItemProcessor<Genre, GenreDoc> genreProcessor,
                                   MongoItemWriter<GenreDoc> genreWriter) {
        return stepBuilderFactory.get("genreMigrationStep")
                .<Genre, GenreDoc>chunk(3)
                .reader(genreReader)
                .processor(genreProcessor)
                .writer(genreWriter)
                .allowStartIfComplete(Boolean.TRUE)
                .build();
    }
}
