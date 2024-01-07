export class user {

  constructor( public id: string,
    public username: string,
    public firstName: string,
    public lastName: string,
    public password: string,
    public email: string,
    public attributes: { [key: string]: string } | null,
    public role: string,
    public createdTimestamp:number,
    ){}
}
