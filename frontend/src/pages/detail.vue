<template>
  <div class="m-4 border px-6 rounded-xl">
    <div class="text-xl font-semibold py-3 border-b mb-4"> 小说详情 </div>
    <div class="flex flex-col">
      <BookList v-model:book="book" />
      <div class="flex gap-3 mt-8 items-center">
        <router-link
          :to="{
            name: 'read',
            query: { queue: 0, bookId: bookId },
          }"
        >
          <n-button type="primary"> 开始阅读</n-button>
        </router-link>
        <n-button type="warning" @click="addShelf">加入书架</n-button>
        <div class="flex flex-col border-l pl-4">
          <div>收藏人数 </div>
          <div>1200</div>
        </div>
      </div>
    </div>

    <div class="text-xl font-semibold py-3 border-b mb-4 mt-15"> 章节目录 </div>
    <n-grid x-gap="12" y-gap="24" :cols="3">
      <n-gi v-for="item in data" :key="item.id">
        <router-link
          :to="{
            name: 'read',
            query: { queue: item.queue, bookId: bookId },
          }"
        >
          {{ item.name }}
        </router-link>
      </n-gi>
    </n-grid>

    <div class="w-full flex mt-20 mb-9">
      <n-pagination
        class="mx-auto"
        v-if="pageInfo != null"
        :page="pageInfo.currentPage + 1"
        @update:page="onPageChange"
        :page-count="pageInfo.totalPage"
      />
    </div>
  </div>
  {{ pageInfo.currentPage }}
  {{ pageInfo.totalPage }}
</template>
<script setup lang="ts">
  import { Condition } from '@/model';
  import { fetchGetBook, fetchLoveBook } from '@/service';
  import { fetchQueryChapter } from '@/service/api/chapter';
  import { onMounted, reactive, ref, toRaw, unref } from 'vue';
  import { useRoute } from 'vue-router';

  const route = useRoute();
  const bookId = Number(route.query.bookId?.toString());
  const book = ref<Partial<Dto.Book>>({});

  const pageInfo = ref<Partial<Dto.Page<Dto.Chapter>>>({});
  const condition = reactive<Partial<Condition.Chapter>>({
    pageSize: 99,
    bookId: bookId,
    pageNum: 0,
  });
  const data = ref<Array<Dto.Chapter>>([]);

  const loadData = () => {
    fetchGetBook(bookId).then((res) => {
      if (res.data != null) {
        book.value = res.data;
      }
    });
    fetchQueryChapter(toRaw(unref(condition))).then((res) => {
      if (res.data != null) {
        data.value = res.data.data;
        Object.assign(pageInfo.value, res.data);
      }
    });
  };

  const onPageChange = (page: number) => {
    console.log(page);
    condition.pageNum = page - 1;
    fetchQueryChapter(toRaw(unref(condition))).then((res) => {
      if (res.data != null) {
        data.value = res.data.data;
        Object.assign(pageInfo.value, res.data);
      }
    });
  };

  const addShelf = () => {
    fetchLoveBook(bookId, true).then((res) => {
      window.$message.warning('已加入到书架！');
    });
  };

  onMounted(() => {
    loadData();
  });
</script>
<style scoped></style>
