import {Component, Vue} from "vue-property-decorator";
import PageableTable, {SpringPageable, TableData} from "@/ts/components/PageableTable";
import {Book, BookService} from "@/ts/services/BookService";
import {Inject} from "typescript-ioc";

/**
 * Компонент со списком книг
 */
@Component({
    template: `
    <div class="home">
      <pageable-table ref="table"
                      :loader="loadBooks"
                      size="mini" border
                      :default-sort="{prop: 'id', order: 'descending'}"
                      empty-text="Данные отсутствуют или не загружены">
        <el-table-column prop="id" label="ID" width="100" sortable="custom"></el-table-column>
        <el-table-column prop="title" label="Название книги" sortable="custom"></el-table-column>
        <el-table-column prop="author.fio" label="Автор" sortable="custom"></el-table-column>
        <el-table-column prop="genre.name" label="Жанр" sortable="custom"></el-table-column>
        <el-table-column
          label="Действия"
          width="400">
          <template slot-scope="scope">
            <el-button type="text" size="small" style="color: green;" @click="onBookShow(scope.row.id)">Просмотр</el-button>
            <el-button type="text" size="small" style="color: green;" @click="onBookEdit(scope.row.id)">Редактировать</el-button>
            <el-button type="text" size="small" style="color: green;" @click="showDeleteModal(scope.row.id)">Удалить</el-button>
          </template>
        </el-table-column>
      </pageable-table>
      <el-dialog
          title="Удаление книги"
          :visible.sync="deleteModalVisible">
        <span>Вы уверены, что хотите удалить книгу?</span>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="deleteModalVisible = false">Отмена</el-button>
            <el-button type="success" @click="onBookDelete()">Удалить</el-button>
          </span>
        </template>
      </el-dialog>
    </div>
  `,
    components: {
        PageableTable,
    },
})
export default class HomeView extends Vue {

    /** Описание справочников */
    $refs!: {
        table: PageableTable<Book>
    };

    /** Сервис по работе с книгами */
    @Inject private readonly bookService: BookService;

    /** Признак отображения диалогового окна удаления книги */
    private deleteModalVisible = false;
    /** Обрабатываемая книга операции удаления */
    private bookIdToDelete: number;

    /**
     * Возвращает список книг для таблицы
     * @param pageable запрос на пагинацию
     */
    private async loadBooks(pageable: SpringPageable): Promise<TableData<Book>> {
        return await this.bookService.loadBooks(pageable);
    }

    /**
     * Обработчик просмотра книги
     * @param id идентификатор книги
     */
    private async onBookShow(id: number) {
        await this.$router.push(`/book/${id}`);
    }

    /**
     * Обработчик просмотра книги
     * @param id идентификатор книги
     */
    private async onBookEdit(id: number) {
        await this.$router.push(`/book/editor/${id}`);
    }

    /**
     * Обработчик нажатия кнопки Удалить
     * @param id идентификатор книги
     * @private
     */
    private showDeleteModal(id: number): void {
        this.bookIdToDelete = id;
        this.deleteModalVisible = true;
    }

    /**
     * Обработчик удаления книги
     */
    private async onBookDelete() {
        await this.bookService.deleteBook(this.bookIdToDelete);
        await this.$refs.table.refresh(false);
        this.deleteModalVisible = false;
    }
}
