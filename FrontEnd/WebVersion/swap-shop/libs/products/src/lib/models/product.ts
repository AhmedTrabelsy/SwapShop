import { Category } from "./category";

export class product{
    id?: string;
    category!: Category[];
    name!: string;
    description!: string;  
    price!:number;
    category_id!: number;
    created_at!: Date;
    updated_at!: Date;

}