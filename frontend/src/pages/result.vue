<template>
  <div class="w-full flex justify-center">
    <div class="w-55vw">
      <div class="h-12"></div>
      <template v-for="item of data" :key="item">
        <router-link :to="{ name: 'detail', query: { bookId: item.id } }">
          <BookList :book="item" class="mb-12" />
        </router-link>
      </template>
    </div>
  </div>
</template>
<script setup lang="ts">
  import { fetchQueryBook } from '@/service';
  import { useRoute, useRouter } from 'vue-router';
  import { ref } from 'vue';

  const data = ref<Array<Dto.Book>>([]);
  const route = useRoute();

  const bookName = route.query.key?.toString();
  fetchQueryBook({ name: bookName }).then((res) => {
    if (res.data != null) {
      data.value = res.data.data;
    }
  });

  const router = useRouter();
  const jump2Detail = () => {
    router.push('/detail');
  };
</script>
<style scoped></style>
