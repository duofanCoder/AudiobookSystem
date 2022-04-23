<template>
  <n-card title="用户管理" class="h-full shadow-sm rounded-16px m-5 w-full w-auto">
    <n-space vertical>
      <n-space justify="start" align="center">
        <div>用户昵称</div>
        <n-input v-model:value="condition.name" placeholder="请输入关键字搜索"></n-input>
        <n-button type="primary" @click="clickEvent('search')">搜索</n-button>
      </n-space>
      <n-space justify="start" align="center">
        <n-button type="success" @click="clickEvent('add')">添加</n-button>
        <n-button type="warning" @click="clickEvent('update')">修改</n-button>
        <n-button type="error" @click="clickEvent('remove')">删除</n-button>
        <n-button type="primary" @click="clickEvent('reload')">刷新</n-button>

        <n-button type="success" @click="clickEvent('password')">重置密码</n-button>
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
      title="用户管理"
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
        <n-form-item label="昵称" path="nickname">
          <n-input v-model:value="model.nickname" placeholder="请输入姓名" />
        </n-form-item>
        <n-form-item label="用户名" path="username">
          <n-input v-model:value="model.username" placeholder="请输入用户名" />
        </n-form-item>
        <n-form-item label="性别" path="gender">
          <n-space>
            <n-radio :checked="model.gender === false" value="false" @change="handleRadioChange">
              男</n-radio
            >
            <n-radio :checked="model.gender === true" value="true" @change="handleRadioChange">
              女</n-radio
            >
          </n-space>
        </n-form-item>
        <n-form-item label="手机号" path="mobile">
          <n-input v-model:value="model.mobile" placeholder="请输入手机号" />
        </n-form-item>
        <n-date-picker
          :default-value="defaultBirthTimeRef"
          type="date"
          @update:value="selectBirth"
          input-readonly
        />
        <div style="display: flex; justify-content: flex-end">
          <n-button round type="primary" @click="submitClickEvent"> 保存信息</n-button>
        </div>
      </n-form>
    </n-card>
  </n-modal>
</template>
<script setup lang="ts">
  import { computed, onMounted, reactive, ref, toRaw, unref } from 'vue';
  import {
    fetchQueryUser,
    fetchRemoveUser,
    fetchSaveUser,
    fetchUpdateUser,
    fetchResetUser,
  } from '@/service';

  const condition = reactive<Partial<Condition.User>>({});
  const model = ref<Partial<Dto.User>>({});
  const columns = [
    {
      type: 'selection',
    },
    {
      title: '序号',
      key: 'id',
    },
    {
      title: '昵称',
      key: 'nickname',
    },
    {
      title: '用户名',
      key: 'username',
    },
    {
      title: '性别',
      key: 'gender',
      render(row: Dto.User) {
        return row.gender ? '女' : '男';
      },
    },
    {
      title: '手机号',
      key: 'mobile',
    },
    {
      title: '生日',
      key: 'birth',
    },
    {
      title: '注册时间',
      key: 'createTime',
    },
  ];
  const data = ref<Array<Dto.User>>([]);

  interface Select {
    label: string;
    value: number;
  }
  const defaultBirthTimeRef = computed(() => {
    if (model.value.birth != null) {
      return new Date(model.value.birth).getTime();
    } else {
      return null;
    }
  });
  const selectBirth = (_value: number | null, formattedValue: string | null) => {
    if (formattedValue) {
      model.value.birth = formattedValue;
    }
  };
  const reload = () => {
    fetchQueryUser(toRaw(unref(condition))).then((res) => {
      if (res.data?.data != undefined) {
        data.value = res.data?.data;
      }
    });
  };

  onMounted(() => {
    reload();
  });

  const handleRadioChange = (e: Event) => {
    if ((e.target as HTMLInputElement).value === 'true') {
      model.value.gender = true;
    } else {
      model.value.gender = false;
    }
  };
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
  const handleGroupClick = (groupId: number) => {};
  const showModalRef = ref(false);
  const submitClickEventRef = ref('');
  const clickEvent = (type: string) => {
    const checkedRows = toRaw(unref(checkedRowKeysRef));
    switch (type) {
      case 'add': {
        model.value = {} as Partial<Dto.User>;
        model.value.password = '123456';
        showModalRef.value = true;
        submitClickEventRef.value = 'add';
        break;
      }
      case 'password': {
        if (checkedRows.length === 0) {
          window.$message?.info('请正确选择！');
        }
        checkedRows.forEach((item) => fetchResetUser(item));
        window.$message?.info('重置新密码为123456！');
        break;
      }
      case 'update': {
        if (checkedRows.length !== 1) {
          window.$message?.info('请正确选择！');
          return;
        }

        data.value.forEach((item) => {
          if (item.id === checkedRows[0]) {
            Object.assign(model.value, unref(toRaw(unref(item))));
            console.log('找到了被复制：');
            console.log(toRaw(unref(model)));
          }
        });
        showModalRef.value = true;
        submitClickEventRef.value = 'update';
        break;
      }
      case 'remove': {
        fetchRemoveUser(checkedRowKeysRef.value).then((res) => {
          window.$message?.success('删除成功！');
          reload();
        });
        break;
      }
      case 'search': {
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
        console.log(toRaw(unref(model)));
        fetchUpdateUser(toRaw(unref(model))).then((res) => {
          reload();
          showModalRef.value = false;
          window.$message?.info('修改成功！');
        });
        break;
      }
      case 'add': {
        fetchSaveUser(toRaw(unref(model))).then((res) => {
          reload();
          showModalRef.value = false;
          window.$message?.info('添加成功！');
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
