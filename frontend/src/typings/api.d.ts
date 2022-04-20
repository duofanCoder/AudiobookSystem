declare namespace API {
  interface ResponseResult<T> {
    code: number;
    message: string;
    data: T;
  }
  // currentKey: 'pageNum',
  // pageSizeKey: 'pageSize',
  // totalKey: 'total',
  // totalPageKey: 'pages',
  interface PageResult<T> {
    hasNextPage: boolean;
    hasPreviousPage: boolean;
    nextPage: number;
    prePage: number;
    total: number;
    list: Array<T>;

    pageNum: number;
    pages: number;
    pageSize: number;
  }

  interface Article {
    id: number;
    createTime: string;
    updateTime: string;
    userName: string;
    passWord: string | number;
    userId: number;
    title: string;
    content: string;
    top: boolean;
    status: boolean;
    typeId: number;
    description: string;
    tags: Array<Tags>;
    bizType: Type;
    lookCount: number;
    commentCount: number;
    loveCount: number;
  }
  interface Tags {
    id: number;
    createTime: Date;
    updateTime: Date;
    name: string;
    description: string;
  }
  interface Type {
    id: number;
    createTime: Date;
    updateTime: Date;
    name: string;
    description: string;
    sort: number;
    articleCount: number;
    available: boolean;
  }

  interface UserLogin {
    username: string;
    password: string;
    rememberMe: boolean;
  }

  interface User {
    id: number;
    username: string;
    nickname: string;
    password: string;
  }
  interface File {
    size: number;
    suffix: string;
    createTime: string;
    updateTime: string;
    uploadStartTime: string;
    height: string;
    originalFileName: string;
    fullFilePath: string;
    filePath: string;
    uploadEndTime: string;
  }
}
