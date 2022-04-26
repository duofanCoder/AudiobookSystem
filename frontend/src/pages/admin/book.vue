<template>
  <n-card title="新闻书籍管理" class="h-full shadow-sm rounded-16px">
    <n-space vertical>
      <n-space justify="start" align="center">
        <div>书籍名称</div>
        <n-input v-model:value="queryKeyRef" placeholder="请输入关键字搜索"></n-input>
        <n-button type="primary" @click="clickEvent('search')">搜索</n-button>
      </n-space>
      <n-space justify="start" align="center">
        <n-button type="success" @click="clickEvent('add')">添加</n-button>
        <n-button type="warning" @click="clickEvent('update')">修改</n-button>
        <n-button type="error" @click="clickEvent('remove')">删除</n-button>
        <n-button type="primary" @click="clickEvent('reload')">刷新</n-button>
      </n-space>

      <n-data-table
        ref="table"
        :columns="columns"
        :data="data"
        :pagination="pagination"
        :row-key="(row) => row.id"
        @update:checked-row-keys="handleCheck"
      />
    </n-space>
  </n-card>
  <n-modal v-model:show="showModalRef">
    <n-card
      style="width: 600px"
      title="新闻书籍管理"
      :bordered="false"
      size="huge"
      role="dialog"
      aria-modal="true"
    >
      <n-form
        ref="formRef"
        :model="model"
        label-placement="left"
        label-width="auto"
        require-mark-placement="right-hanging"
        :style="{
          maxWidth: '640px',
        }"
      >
        <n-form-item label="名称" path="name">
          <n-input v-model:value="model.name" placeholder="请输入名称" />
        </n-form-item>
        <n-form-item label="分类" path="category">
          <n-select v-model:value="model.category" :options="categoryOptions" />
        </n-form-item>
        <n-form-item label="简介" path="description">
          <n-input v-model:value="model.description" placeholder="请输入简介" type="textarea" />
        </n-form-item>
        <n-form-item label="封面" path="img">
          <upload v-model:filePath="model.img" />
        </n-form-item>
        <n-form-item label="作者" path="author">
          <n-input v-model:value="model.author" placeholder="请输入作者" />
        </n-form-item>
        <n-form-item label="书籍" path="">
          <n-upload ref="upload" :max="1" @change="onUploadNovel" :default-upload="false">
            <n-button>选择文件</n-button>
          </n-upload>
        </n-form-item>
        <div style="display: flex; justify-content: flex-end">
          <n-button round type="primary" @click="submitClickEvent"> 保存信息</n-button>
        </div>
      </n-form>
    </n-card>
  </n-modal>
</template>
<script setup lang="ts">
  import { onMounted, reactive, ref, toRaw, unref } from 'vue';
  import { fetchQueryBook, fetchRemoveBook, fetchSaveBook, fetchUpdateBook } from '@/service';
  import { Condition } from '@/model';
  import { Category } from '@/model/enum/business';
  import { UploadFileInfo } from 'naive-ui';

  const queryKeyRef = ref('');
  const model = ref<Partial<Dto.Book>>({});
  const columns = [
    {
      type: 'selection',
    },
    {
      title: '序号',
      key: 'id',
      width: '80px',
    },
    {
      title: '名称',
      key: 'name',
      width: '120px',
    },
    {
      title: '分类',
      key: 'category',
      width: '120px',
    },

    {
      title: '描述',
      key: 'description',
    },
    {
      title: '添加时间',
      key: 'createTime',
      width: '150px',
    },
  ];
  const onUploadNovel = (fileInfo: UploadFileInfo) => {
    if (fileInfo.file != null) {
      model.value.file = fileInfo.file;
    }
  };
  const categoryOptions = [
    {
      label: '玄幻奇幻',
      value: Category.玄幻奇幻,
    },
    {
      label: '武侠仙侠',
      value: Category.武侠仙侠,
    },
    {
      label: '女频言情',
      value: Category.女频言情,
    },
    {
      label: '现代都市',
      value: Category.现代都市,
    },
    {
      label: '历史军事',
      value: Category.历史军事,
    },
  ];
  const data = ref<Array<Dto.Book>>([]);
  const condition = reactive<Partial<Condition.Common>>({});
  const reload = () => {
    fetchQueryBook(condition).then((res) => {
      if (res.data != undefined) {
        data.value = res.data?.data;
      }
    });
  };
  onMounted(() => {
    reload();
  });

  const checkedRowKeysRef = ref([]);
  const pagination = reactive({
    pageSize: 10,
    showSizePicker: true,
    pageSizes: [10, 20, 30],
    onUpdatePage: (page: number) => {
      condition.pageNum = page;
      reload();
    },
    onUpdatePageSize: (pageSize: number) => {
      condition.pageSize = pageSize;
      condition.pageNum = 0;
      pagination.pageSize = pageSize;
      reload();
    },
  });
  const handleCheck = (rowKeys: []) => {
    checkedRowKeysRef.value = rowKeys;
  };
  const showModalRef = ref(false);
  const submitClickEventRef = ref('');
  const clickEvent = (type: string) => {
    const checkedRows = toRaw(unref(checkedRowKeysRef));
    switch (type) {
      case 'addNovel': {
        console.log('xiugaile');
        return;
      }
      case 'add': {
        model.value.name = '';
        model.value.description = '';
        model.value.id = undefined;
        showModalRef.value = true;

        submitClickEventRef.value = 'add';
        break;
      }
      case 'update': {
        if (checkedRows.length !== 1) {
          window.$message?.info('请正确选择！');
          return;
        }
        data.value.forEach((item: Dto.Book) => {
          if (item.id === checkedRows[0]) {
            Object.assign(model.value, unref(toRaw(unref(item))));
          }
        });
        showModalRef.value = true;
        submitClickEventRef.value = 'update';
        break;
      }
      case 'remove': {
        if (checkedRowKeysRef.value.length == 0) {
          window.$message.info('请正确选择!');
          return;
        }
        fetchRemoveBook(checkedRowKeysRef.value).then((res) => {
          window.$message?.success('删除成功！');
          checkedRowKeysRef.value = [];
          reload();
        });
        break;
      }
      case 'search': {
        condition.name = queryKeyRef.value;
        reload();
        break;
      }
      case 'reload': {
        reload();
        break;
      }
      default:
        break;
    }
  };

  const submitClickEvent = () => {
    switch (submitClickEventRef.value) {
      case 'update': {
        fetchUpdateBook(model.value).then((res) => {
          reload();
          showModalRef.value = false;
          window.$message?.info('修改成功！');
        });
        break;
      }
      case 'add': {
        fetchSaveBook(model.value).then((res) => {
          if (res.error == null) {
            reload();
            showModalRef.value = false;
            window.$message?.info('添加成功！');
          }
        });
        break;
      }
      default: {
        break;
      }
    }
  };
</script>
<style scoped></style>
