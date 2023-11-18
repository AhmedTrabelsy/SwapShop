export class Category{
  constructor( public id:number,public name:string,public icon:string,public subcategories:Category[]){}
}
