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
    title: string;
    author: string;
    img: string;
    description: string;
  }
}
