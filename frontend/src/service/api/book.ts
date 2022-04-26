import { Condition, EnumContentType } from '@/model';
import { request } from '../request';

export function fetchQueryBook(condition: Partial<Condition.Book>) {
  return request.post<Dto.Page<Dto.Book>>('/book/query', condition);
}
export function fetchGetLoveBook() {
  return request.get<Array<Dto.Book>>('/book/love');
}

export function fetchLoveBook(bookId: number, isLove: boolean) {
  const params = new URLSearchParams();
  params.append('bookId', String(bookId));
  params.append('isLove', String(isLove));
  return request.post('/book/love?' + params.toString());
}
export function fetchRemoveBook(ids: number[]) {
  const searchParams = new URLSearchParams();
  ids.forEach((id) => searchParams.append('primaryKeys', id.toString()));
  return request.post(`/book/remove?${searchParams.toString()}`);
}
export function fetchGetBook(id: number) {
  const searchParams = new URLSearchParams();
  searchParams.append('primaryKey', String(id));
  return request.post(`/book/get?${searchParams.toString()}`);
}
export function fetchSaveBook(book: Partial<Dto.Book>) {
  return request.post('/book/save', book, {
    headers: { 'Content-Type': EnumContentType.formData },
  });
}

export function fetchUpdateBook(book: Partial<Dto.Book>) {
  return request.post('/book/update', book);
}
