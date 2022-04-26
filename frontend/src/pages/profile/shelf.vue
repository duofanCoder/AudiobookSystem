<template>
  <div class="flex flex-col flex-1 mx-auto max-w-235">
    <div class="text-center">
      <p class="font-medium text-3xl mt-12"> 书架 </p>
      <p class="mt-3 text-lg">查看您收藏过的书籍。</p>
    </div>

    <div class="border py-5 mt-6 rounded-xl bg-white px-6">
      <p class="text-2xl">书籍</p>
      <p class="mt-2 tracking-widest mb-4">收藏过的书籍都会出现在这里。</p>
      <!-- w-50 -->
      <template v-for="item in data" :key="item.id">
        <div class="flex mb-6 justify-between">
          <router-link :to="{ name: 'detail', query: { bookId: item.id } }">
            <BookList :book="item" class="pr-12 hover:cursor-pointer" />
          </router-link>

          <div class="h-full flex">
            <n-button type="error" @click="removeLove(item.id)" class="mb-auto">移除</n-button>
          </div>
        </div>
      </template>
    </div>
  </div>
</template>

<script setup lang="ts">
  import { fetchGetBook, fetchGetLoveBook, fetchLoveBook } from '@/service';
  import { onMounted, ref } from 'vue';
  import { useRouter } from 'vue-router';

  const data = ref<Array<Dto.Book>>([]);
  const router = useRouter();
  const jump2Detail = () => {
    router.push('/detail');
  };

  const removeLove = (bookId: number) => {
    fetchLoveBook(bookId, false).then((res) => {
      loadData();
    });
  };

  const loadData = () => {
    fetchGetLoveBook().then((res) => {
      if (res.data != null) {
        data.value = res.data;
      }
    });
  };

  onMounted(() => {
    loadData();
  });
</script>

<style scoped></style>
