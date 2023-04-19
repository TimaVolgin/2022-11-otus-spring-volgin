import { Component, Vue } from "vue-property-decorator";

@Component({
    template: `
  <div id="app">
    <el-menu
      router
      mode="horizontal"
      background-color="#90EE90"
      text-color="#000000"
      active-text-color="#006400">
      <el-menu-item index="/">Главная</el-menu-item>
      <el-menu-item index="/book/editor/new">Добавить книгу</el-menu-item>
    </el-menu>
      
    <el-container>
      <el-main>
        <router-view :key="$route.path"/>
      </el-main>
    </el-container>
  </div>
  `
})
export default class App extends Vue {
}
