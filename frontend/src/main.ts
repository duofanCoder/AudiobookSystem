import Provider from '@/layouts/Provider.vue';
import { setupRouter } from '@/router';
import { createApp } from 'vue';
import 'virtual:windi.css';
import { setupStore } from '@/store';
import { setGlobalOptions } from 'vue-request';

setGlobalOptions({
  pagination: {
    currentKey: 'pageNum',
    pageSizeKey: 'pageSize',
    totalKey: 'data.total',
    totalPageKey: 'data.pages',
  },
});
const app = createApp(Provider);
setupRouter(app);
setupStore(app);
app.mount('#app');
