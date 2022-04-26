declare namespace Dto {
  interface Token {
    prefix: string;
    expiration: number;
    token: string;
  }

  interface Page<T> {
    currentPage: number;
    data: Array<T>;
    totalPage: number;
  }

  interface User {
    id: number;
    username: string;
    password: string;
    nickname: string;
    mobile: string;
    email: string;
    role: string;
    avatar: string;
    birth: string;
    gender: boolean;
    shelf: Array<Book>;
  }

  interface Book {
    id: number;
    name: string;
    author: string;
    category: string;
    img: string;
    createTime: string;
    updateTime: string;
    description: string;
    file: File;
    chapterList: Array<Dto.Chapter> | undefined;
  }

  interface Chapter {
    id: number;
    name: string;
    createTime: string;
    updateTime: string;
    description: string;
    content: string;
    queue: number;
  }
}
