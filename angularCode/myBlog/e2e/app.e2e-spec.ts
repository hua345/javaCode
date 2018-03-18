import { MyBlogPage } from './app.po';

describe('my-blog App', () => {
  let page: MyBlogPage;

  beforeEach(() => {
    page = new MyBlogPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
