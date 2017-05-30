import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AppRegisterComponent } from './app-register.component';

describe('AppRegisterComponent', () => {
  let component: AppRegisterComponent;
  let fixture: ComponentFixture<AppRegisterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AppRegisterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppRegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
