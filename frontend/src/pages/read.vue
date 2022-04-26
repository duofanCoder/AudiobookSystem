<template>
  <div class="flex gap-4 mt-9 px-10">
    <div class="">
      <div
        @click="doAction('speech')"
        class="hover:cursor-pointer flex items-center border border-yellow-300 justify-center bg-yellow-100 w-12 h-12 my-auto"
      >
        <i-ic-baseline-headset-mic class="text-green-500 w-6 h-6" />
      </div>

      <div>
        <audio ref="audioRef"></audio>
      </div>
      <div
        class="flex items-center border border-yellow-300 cursor-pointer justify-center bg-yellow-100 w-12 h-12 my-auto"
      >
        <i-mdi-bookshelf class="text-green-500 w-6 h-6" />
      </div>
    </div>

    <div class="flex flex-col gap-3">
      <div
        class="flex h-12 px-5 rounded bg-yellow-100 justify-between items-center border border-yellow-300"
      >
        <div @click="getContent(chapterRef.bookId, chapterRef.queue - 1)">
          <div> 上一章</div>
        </div>

        <div>斗破苍穹</div>
        <div @click="getContent(chapterRef.bookId, chapterRef.queue + 1)">
          <div>下一章</div>
        </div>
      </div>

      <div class="bg-yellow-100 rounded px-9 border border-yellow-300">
        <div class="flex font-bold text-xl justify-center items-center h-15">
          <span class="align-middle"> {{ chapterRef.name }}</span>
        </div>
        <div class="whitespace-pre-wrap">
          {{ chapterRef.content }}
        </div>
      </div>
      <div class="text-center my-8"> 到底了 </div>
    </div>
  </div>
</template>
<script setup lang="ts">
  import { fetchGetChapter } from '@/service/api/chapter';
  import { reactive, ref } from 'vue';
  import { useRoute } from 'vue-router';

  const chapterRef = reactive<Partial<Dto.Chapter>>({});
  const route = useRoute();

  const queue = Number(route.query.queue);
  const bookId = Number(route.query.bookId?.toString());

  const getContent = (bookId: number, queue: number) => {
    fetchGetChapter(bookId, queue).then((res) => {
      if (res.data != null) {
        Object.assign(chapterRef, res.data);
      }
    });
  };
  getContent(bookId, queue);

  const audioRef = ref<HTMLAudioElement | null>(null);
  const doAction = (action: string) => {
    switch (action) {
      case 'speech':
        if (chapterRef.content == undefined) {
          return;
        }
        const speakUrl =
          'https://fanyi.sogou.com/reventondc/synthesis?speed=1&lang=zh-CHS&from=translateweb&speaker=6&text=';
        audioRef.value = audioRef.value as HTMLAudioElement;
        if (audioRef.value.paused == true) {
          const num = chapterRef.content.length / 500;
          const subArticleArr = new Array(); //分段阅读，每三百字三百字读，读完第一段读第二段
          for (let i = 0; i < num; i++) {
            const subText = chapterRef.content.substring(0 + i * 500, 500);
            subArticleArr.push(subText);
          }
          let fenIndex = 0;
          audioRef.value.src = speakUrl + subArticleArr[fenIndex];
          audioRef.value.play();
          audioRef.value.addEventListener(
            'ended',
            () => {
              fenIndex++; //下标+1 读下一段
              if (audioRef.value != null) {
                if (fenIndex <= subArticleArr.length) {
                  audioRef.value.src = speakUrl + subArticleArr[fenIndex];
                  audioRef.value.play(); // 这个就是播放
                } else {
                  audioRef.value.pause();
                }
              }
            },
            false
          );
        } else {
          audioRef.value.pause();
        }
        break;
      default:
        break;
    }
  };
</script>
<style scoped></style>
