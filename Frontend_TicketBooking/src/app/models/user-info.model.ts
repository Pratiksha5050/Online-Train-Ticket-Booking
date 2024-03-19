// user-info.model.ts
export class UserInfo {
    constructor(
      public password: string,
      public userName: string,
      public firstName: string,
      public middleName: string | null,
      public lastName: string,
      public dateOfBirth: Date,
      public gender: string,
      public mobileNumber: string,
      public email: string,
      public nationality: string,
     public role:"USER"
    ) {}
  }
  