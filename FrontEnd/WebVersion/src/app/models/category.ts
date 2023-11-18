export class Category{
  constructor( public id:number,public name:string,public icon:string,public subcategories:Category[]){}

  get iconUrl():string{
    console.log('Icon URL:', this.icon);

    return "http://34.199.239.78:8888/CATEGORY-SERVICE"+this.icon;

  }
}
