<template>
  <div class="flex gap-4">
    <img v-if="book.img != null" class="h-22 w-auto mx-auto my-auto shadow-lg" :src="book.img" />
    <div class="flex flex-col gap-2 w-full">
      <div class="font-semibold"> {{ book != null ? book.name : 'kong' }} </div>
      <n-ellipsis class="text-gray-400 text-[0.2rem] h-48px" :line-clamp="4" :tooltip="false">
        {{ book != null ? book.description : 'kong' }}
      </n-ellipsis>
      <div class="text-xs"> {{ book != null ? book.author : 'kong' }} </div>
    </div>
  </div>
</template>
<script setup lang="ts">
  const { book } = defineProps({
    book: {
      type: Object,
      default: null,
    },
  });
</script>
<style scoped></style>
