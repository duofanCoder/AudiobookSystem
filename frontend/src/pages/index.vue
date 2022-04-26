<template>
  <div class="mb-20">
    <n-carousel draggable show-arrow autoplay>
      <img
        class="carousel-img"
        src="https://naive-ui.oss-cn-beijing.aliyuncs.com/carousel-img/carousel1.jpeg"
      />
      <img
        class="carousel-img"
        src="https://naive-ui.oss-cn-beijing.aliyuncs.com/carousel-img/carousel2.jpeg"
      />
      <img
        class="carousel-img"
        src="https://naive-ui.oss-cn-beijing.aliyuncs.com/carousel-img/carousel3.jpeg"
      />
      <img
        class="carousel-img"
        src="https://naive-ui.oss-cn-beijing.aliyuncs.com/carousel-img/carousel4.jpeg"
      />
    </n-carousel>

    <div
      v-if="xhcqBookList.length != 0"
      class="border border-amber-400 mx-12 mt-8 rounded-md py-2 px-4"
    >
      <div class="border-b py-1 text-base text-amber-500"> 玄幻奇幻 </div>

      <div class="flex py-4">
        <n-grid x-gap="12" y-gap="32" :cols="2">
          <n-gi v-for="item in xhcqBookList" :key="item.id">
            <router-link :to="{ name: 'detail', query: { bookId: item.id } }">
              <BookList :book="item" />
            </router-link>
          </n-gi>
        </n-grid>
      </div>
    </div>

    <div
      v-if="wxxxBookList.length != 0"
      class="border border-amber-400 mx-12 mt-8 rounded-md py-2 px-4"
    >
      <div class="border-b py-1 text-base text-amber-500"> 武侠仙侠 </div>

      <div class="flex py-4">
        <n-grid x-gap="12" y-gap="32" :cols="2">
          <n-gi v-for="item in wxxxBookList" :key="item.id">
            <router-link :to="{ name: 'detail', query: { bookId: item.id } }">
              <BookList :book="item" />
            </router-link>
          </n-gi>
        </n-grid>
      </div>
    </div>

    <div
      v-if="npyqBookList.length != 0"
      class="border border-amber-400 mx-12 mt-8 rounded-md py-2 px-4"
    >
      <div class="border-b py-1 text-base text-amber-500"> 女频言情 </div>
      <div class="flex py-4">
        <n-grid x-gap="12" y-gap="32" :cols="2">
          <n-gi>
            <BookList />
          </n-gi>
          <n-gi>
            <BookList />
          </n-gi>
          <n-gi>
            <BookList />
          </n-gi>
          <n-gi>
            <BookList />
          </n-gi>
        </n-grid>
      </div>
    </div>

    <div
      v-if="xddsBookList.length != 0"
      class="border border-amber-400 mx-12 mt-8 rounded-md py-2 px-4"
    >
      <div class="border-b py-1 text-base text-amber-500"> 现代都市 </div>
      <div class="flex py-4">
        <n-grid x-gap="12" y-gap="32" :cols="2">
          <n-gi v-for="item in wxxxBookList" :key="item.id">
            <router-link :to="{ name: 'detail', query: { bookId: item.id } }">
              <BookList :book="item" />
            </router-link>
          </n-gi>
        </n-grid>
      </div>
    </div>

    <div
      v-if="lsjsBookList.length != 0"
      class="border border-amber-400 mx-12 mt-8 rounded-md py-2 px-4"
    >
      <div class="border-b py-1 text-base text-amber-500"> 历史军事 </div>
      <div class="flex py-4">
        <n-grid x-gap="12" y-gap="32" :cols="2">
          <n-gi v-for="item in wxxxBookList" :key="item.id">
            <router-link :to="{ name: 'detail', query: { bookId: item.id, book: toJson(item) } }">
              <BookList :book="item" />
            </router-link>
          </n-gi>
        </n-grid>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { Category } from '@/model/enum/business';
  import { fetchQueryBook } from '@/service';
  import { onMounted, ref } from 'vue';

  const xhcqBookList = ref<Array<Dto.Book>>([]);
  const wxxxBookList = ref<Array<Dto.Book>>([]);
  const npyqBookList = ref<Array<Dto.Book>>([]);
  const xddsBookList = ref<Array<Dto.Book>>([]);
  const lsjsBookList = ref<Array<Dto.Book>>([]);

  const loadData = () => {
    fetchQueryBook({ category: Category.玄幻奇幻, pageSize: 6 }).then((res) => {
      if (res.data != undefined) {
        xhcqBookList.value = res.data?.data;
      }
    });
    fetchQueryBook({ category: Category.武侠仙侠, pageSize: 6 }).then((res) => {
      if (res.data != undefined) {
        wxxxBookList.value = res.data?.data;
      }
    });
    fetchQueryBook({ category: Category.女频言情, pageSize: 6 }).then((res) => {
      if (res.data != undefined) {
        npyqBookList.value = res.data?.data;
      }
    });
    fetchQueryBook({ category: Category.现代都市, pageSize: 6 }).then((res) => {
      if (res.data != undefined) {
        xddsBookList.value = res.data?.data;
      }
    });
    fetchQueryBook({ category: Category.历史军事, pageSize: 6 }).then((res) => {
      if (res.data != undefined) {
        lsjsBookList.value = res.data?.data;
      }
    });
  };

  onMounted(() => {
    loadData();
  });
</script>

<style scoped>
  .carousel-img {
    width: 100%;
    height: 240px;
    object-fit: cover;
  }

  .custom-arrow {
    display: flex;
    position: absolute;
    bottom: 25px;
    right: 10px;
  }
</style>
