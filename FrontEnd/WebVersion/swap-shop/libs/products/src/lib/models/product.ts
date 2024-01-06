import { Category } from "./category";
import { Image } from "./image";

export class product{
    id?: string;
    category?: Category;
    name?: string;
    description?: string;
    images?: Image[];
    price?:number;
    categoryID?: number;
    created_at?: Date;
    updated_at?: Date;
}
