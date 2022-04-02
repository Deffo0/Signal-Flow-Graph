export class productionNetworkElement{
  private ID:string;
  private type:string;

  public constructor (ID:string,type:string){
    this.ID = ID
    this.type = type
  }
  public setType(type:string):void{
    this.type = type
  }
  public setID(ID:string):void{
    this.ID = ID
  }
  public getType():string{
    return this.type
  }
  public getID():string{
    return this.ID
  }
}
export default productionNetworkElement;
