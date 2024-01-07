import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ContactCreatorsComponent } from './contact-creators.component';

describe('ContactCreatorsComponent', () => {
	let component: ContactCreatorsComponent;
	let fixture: ComponentFixture<ContactCreatorsComponent>;

	beforeEach(async () => {
		await TestBed.configureTestingModule({
			declarations: [ContactCreatorsComponent]
		}).compileComponents();

		fixture = TestBed.createComponent(ContactCreatorsComponent);
		component = fixture.componentInstance;
		fixture.detectChanges();
	});

	it('should create', () => {
		expect(component).toBeTruthy();
	});
});
