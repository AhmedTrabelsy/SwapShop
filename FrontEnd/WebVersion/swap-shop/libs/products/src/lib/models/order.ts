import { product } from "./product";

export class Order{
    id?: string;
    userId?: string;
    billingAdress?: string;
    product?: product;
    createdAt?: Date;
    updatedAt?: Date;
}