import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'swap-shop-gallery',
  templateUrl: './gallery.component.html',
  styleUrl: './gallery.component.scss'
})
export class GalleryComponent implements OnInit {
  selectedImageUrl?: string;

  @Input() images?: Image[];

  ngOnInit(): void {
    if (this.hasImages) {
      this.selectedImageUrl = 'http://34.199.239.78:8888/PRODUCT-SERVICE/' + this.images![0].name;
    }
  }

  changeSelectedImage(imageUrl?: string) {
    this.selectedImageUrl = imageUrl;
  }

  get hasImages(): boolean {
    return !!this.images && this.images.length > 0;
  }
}

export class Image{
  id?: string;
  name?: string;
}
