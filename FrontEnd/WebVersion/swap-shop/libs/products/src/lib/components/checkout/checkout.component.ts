import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'swap-shop-checkout',
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css'
})
export class CheckoutComponent implements OnInit {
  constructor(private route: ActivatedRoute, private http: HttpClient, private router: Router) { }

  productId?: string;
  billingAddress?: string;


  ngOnInit(): void {
    this.productId = this.route.snapshot.paramMap.get('id') || "";
  }

  submitOrder(): void {
    const order = {
      userId: 8,
      billingAdress: this.billingAddress,
      productId: this.productId
    };

    this.http.post("http://34.199.239.78:8888/ORDER-SERVICE/neworder", order).subscribe(
      () => {
        console.log('Order submitted successfully!');
      },
      (error) => {
        console.error('Error submitting order:', error);
      }

    );
    this.router.navigate(['/success'])
  }
}
