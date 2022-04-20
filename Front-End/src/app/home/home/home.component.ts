import { HomeService } from './home.service';
import { Component, OnInit } from '@angular/core';
import { interval, Observable } from 'rxjs';
import { delay, switchMap } from 'rxjs/operators';
import { Router, RouterLink } from '@angular/router';
import { ResultComponent } from 'src/app/result/result.component';

 //container to hold all different shapes on it
var shapesBack:shapeBack[] = [];

//----------------------------------------------------------------------//

//mapping between shape ID and its area on canvas
let machineArea = new Map<string, Path2D>();
let forwardProductionNetwork = new Map<string, string[]>();
let backwardProductionNetwork = new Map<string, string[]>();
let lineArea = new Map<string, Path2D>();
let lineFuncs = new Map<string, string>();

//----------------------------------------------------------------------//

//flag to activate buttons

var draw_line : shapeBack = null;

//----------------------------------------------------------------------//

//flag to activate buttons of creation

var createLineFlag : boolean = false;
var createdLine : boolean = false;

var createMachineFlag : boolean = false;
var createdMachine : boolean = false;

var createQueueFlag : boolean = false;
var createdQueue : boolean = false;

var machineButtonFlag : boolean = false;
var queueButtonFlag : boolean = false;
var lineButtonFlag : boolean = false;


var tempType : string = "";
var machineCounter : number = 0;
var queueCounter : number = 0;


//----------------------------------------------------------------------//

//global values to stroke color and witdth to assign all shapes to it
var strokeColor:string = 'black';
var strokeWidth:number = 3;

//----------------------------------------------------------------------//
// array for ID generator
var serial = Array.from(Array(1000000).keys());

//----------------------------------------------------------------------//

//ID generator which give ID and remove it from the ID generator array
function get_new_ID():string {
  var ID =   serial.pop()
  return (ID.toString())

}
//----------------------------------------------------------------------//

//shape interface to cover all shapes under restricted contract
export interface shapeBack{
  x:number;
  y:number;
  width:number;
  height:number;
  fiCo:string;
  stCo:string;
  stWi:number;
  type:string;
  is_filled:number;
  shapeID:string;
  order:number;
  func:string;
}

export interface elementsMap {
  elementKey:string[];
  values:string;
}

export interface Product {
  color : string;
}

export interface Machine {
  machineName : string;
  product : Product;
  serviceTime : number;
  prevBufferQueues : Buffer[];
  nexrtBufferQueues : Buffer[];
}

export interface Buffer {
  products : Product[];
  bufferID : string;
  size : number;
}

//----------------------------------------------------------------------//

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})


export class HomeComponent implements OnInit {

  ngOnInit(): void {
    var start : shapeBack;
    var end : shapeBack;
    this.createFunc();

    start={
      x : 25,
      y : 270,
      width : 50,
      height : 50,
      stCo : "black",
      fiCo : "yellow",
      type : "machine",
      is_filled : 1,
      stWi : 2,
      shapeID : "Machine"+machineCounter,
      order:machineCounter,
      func : null,
      }
      createMachineFlag =false;
      createdMachine = true;
      this.placeElement(start, "");
      shapesBack.push(start);
      machineCounter++;
      for(var i = 0; i < shapesBack.length; i++){
        this.placeElement(shapesBack[i], "");
      }
      end={
        x : 1325,
        y : 270,
        width : 50,
        height : 50,
        stCo : "black",
        fiCo : "yellow",
        type : "machine",
        is_filled : 1,
        stWi : 2,
        shapeID : "Machine"+machineCounter,
        order:machineCounter,
        func : null
        }
        createMachineFlag =false;
        createdMachine = true;
        this.placeElement(end, "");
        shapesBack.push(end);
        machineCounter++;
        for(var i = 0; i < shapesBack.length; i++){
          this.placeElement(shapesBack[i], "");
        }

  }

  title = 'Front-End';
  constructor(private server: HomeService, private router :Router, private result :ResultComponent) {}

  playEvent : any;
  replayEvent : any;
  stopReplay :boolean;



  placeElement(shape : shapeBack, fillcolor : string){
    var boardGlobal = (<HTMLCanvasElement>document.getElementById("board"));
    var canvasGlobal = boardGlobal.getContext("2d")!;

    var x = shape.x;
    var y = shape.y;
    var width = shape.width;
    var height = shape.height;
    var stCo = shape.stCo;
    var fiCo = shape.fiCo;
    var stWi = shape.stWi;
    var isfilled = shape.is_filled;
    var type = shape.type;
    var ID = shape.shapeID;
    var order = shape.order;
    var func = shape.func;



    var area:Path2D|null = new Path2D();
    switch(type){
      case "machine":
        if(fillcolor != ""){
          shape.fiCo = fillcolor
          fiCo = fillcolor;
        }
        if(isfilled == 1){
          area.arc(x, y, 0.5*width, 0, 2*Math.PI);
          canvasGlobal.beginPath();
          canvasGlobal.strokeStyle = stCo;
          canvasGlobal.lineWidth = stWi;
          canvasGlobal.fillStyle = fiCo;
          canvasGlobal.arc(x, y, 0.5*width, 0, 2*Math.PI);
          canvasGlobal.fill();
          canvasGlobal.font = "icon";
          if(ID.includes("Machine0")){
            canvasGlobal.strokeText("R", shape.x-(shape.width/30) + 3, shape.y + 1);

          }else if(ID.includes("Machine1")){
            canvasGlobal.strokeText("C", shape.x-(shape.width/30) + 3, shape.y + 1);

          }else{
            canvasGlobal.strokeText((order).toString(), shape.x-(shape.width/30) + 3, shape.y + 1);

          }
          canvasGlobal.textAlign="center";
          canvasGlobal.stroke();

        }
        else{
          area.arc(x, y, 0.5*width, 0, 2*Math.PI);
          canvasGlobal.beginPath();
          canvasGlobal.strokeStyle = stCo;
          canvasGlobal.lineWidth = stWi;
          canvasGlobal.arc(x, y, 0.5*width, 0, 2*Math.PI);
          canvasGlobal.stroke();
        }
        machineArea.set(ID, area);
        area = null;
        break;

        case "line":
          if(width > x){
            area.moveTo(x, y);
            area.quadraticCurveTo(x+Math.abs(x-width)/2,y+Math.abs(x-width)/2,width,height);
            canvasGlobal.beginPath();
            canvasGlobal.strokeStyle = stCo;
            canvasGlobal.lineWidth = stWi;
            canvasGlobal.moveTo(x, y);
            canvasGlobal.quadraticCurveTo(x+Math.abs(x-width)/2,y+Math.abs(x-width)/2,width,height);
            canvasGlobal.stroke();
            var angle=Math.PI+Math.atan2(height-y-Math.abs(x-width)/2,width-x-Math.abs(x-width)/2);
            var angle1=angle+Math.PI/6;
            var angle2=angle-Math.PI/6;
            canvasGlobal.strokeStyle = "white";
            canvasGlobal.font = "30px Comic Sans MS";
            canvasGlobal.textAlign="center";
            canvasGlobal.strokeText(func,x+Math.abs(x-width)/2,y+Math.abs(y-(y+Math.abs(x-width)/2))/2);
            canvasGlobal.beginPath();
            canvasGlobal.strokeStyle = stCo;
            canvasGlobal.lineWidth = stWi;
            canvasGlobal.fillStyle = "darkred"
            canvasGlobal.moveTo(width, height);
            canvasGlobal.arc(width,height,20,angle1,angle2,true);
            canvasGlobal.lineTo(width, height);
            canvasGlobal.fill();
            canvasGlobal.closePath();
          }else{
            area.moveTo(x, y);
            area.quadraticCurveTo(x-Math.abs(x-width)/2,y-Math.abs(x-width)/2,width,height);
            canvasGlobal.beginPath();
            canvasGlobal.strokeStyle = stCo;
            canvasGlobal.lineWidth = stWi;
            canvasGlobal.moveTo(x, y);
            canvasGlobal.quadraticCurveTo(x-Math.abs(x-width)/2,y-Math.abs(x-width)/2,width,height);
            canvasGlobal.stroke();
            var angle=Math.PI+Math.atan2(height-y+Math.abs(x-width)/2,width-x+Math.abs(x-width)/2);
            var angle1=angle+Math.PI/6;
            var angle2=angle-Math.PI/6;
            canvasGlobal.strokeStyle = "white";
            canvasGlobal.font = "30px Comic Sans MS";
            canvasGlobal.textAlign="center";
            canvasGlobal.strokeText(func,x-Math.abs(x-width)/2,y-Math.abs(y-(y-Math.abs(x-width)/2))/2);
            canvasGlobal.beginPath();
            canvasGlobal.strokeStyle = stCo;
            canvasGlobal.lineWidth = stWi;
            canvasGlobal.fillStyle = "darkred"
            canvasGlobal.moveTo(width, height);
            canvasGlobal.arc(width,height,20,angle1,angle2,true);
            canvasGlobal.lineTo(width, height);
            canvasGlobal.fill();
            canvasGlobal.closePath();
          }

          lineArea.set(ID, area);
          area = null;

          break;

        default:
          break;
    }
  }

//----------------------------------------------------------------------//

  confirm_stroke() {
    var sc = <HTMLInputElement>document.getElementById("stroke_color");
    strokeColor = sc.value;
    var sw = <HTMLInputElement>document.getElementById("stroke_width");
    var strwid : number = parseInt(sw.value);
    strokeWidth = strwid;
  }

  createFunc(){
    var boardGlobal = (<HTMLCanvasElement>document.getElementById("board"));
    var canvasGlobal = boardGlobal.getContext("2d")!;


    boardGlobal.addEventListener("mousedown", e => {
      for(var shape of shapesBack){
        if(shape.type == "line"){
          if(canvasGlobal.isPointInPath(lineArea.get(shape.shapeID),e.offsetX,e.offsetY)){

            let func = prompt("Enter a Transfer Function");
            shape.func = func;
            canvasGlobal.clearRect(0,0,1380,675);
            for(var i = 0; i < shapesBack.length; i++){
              this.placeElement(shapesBack[i], "");
            }
            lineFuncs.set(draw_line.shapeID, func);

          }
        }
      }

    });
  }

//----------------------------------------------------------------------//
    createLine(){
      createMachineFlag = false;
      createQueueFlag = false;

      createdMachine = false;
      createdQueue = false;

      var boardGlobal = (<HTMLCanvasElement>document.getElementById("board"));
      var canvasGlobal = boardGlobal.getContext("2d")!;



      createLineFlag = true;
      createdLine = false;

      var fromElement:string;
      var selectLine = false;
      boardGlobal.addEventListener("mousedown",e=>{
        if(!createdLine &&  lineButtonFlag ){
          for(var shape of shapesBack){
              if(shape.type == "machine"){
                if(canvasGlobal.isPointInPath( machineArea.get(shape.shapeID),e.offsetX,e.offsetY)){
                  draw_line={
                    x : e.offsetX,
                    y :e.offsetY,
                    width : 0,
                    height : 0,
                    stCo : "darkred",
                    fiCo : "black",
                    type : "line",
                    is_filled : 1,
                    stWi : 2.50,
                    shapeID : get_new_ID(),
                    order:0,
                    func : "1"

                    }
                    selectLine = true;
                    createdLine = true
                    tempType = "machine";
                    fromElement = shape.shapeID;

                }
              }
          }
        }
      });

      boardGlobal.addEventListener("mousemove", e => {
        if(createLineFlag && selectLine && (draw_line != null) && lineButtonFlag && createdLine){
          canvasGlobal.clearRect(0,0,1380,675);
          draw_line.width = e.offsetX;
          draw_line.height = e.offsetY;

          this.placeElement(draw_line, "");
          for(var i = 0; i < shapesBack.length; i++){
            this.placeElement(shapesBack[i], "");
          }
        }

      });
      boardGlobal.addEventListener("mouseup", e => {
        if(lineButtonFlag && fromElement != null){
          for(var shape of shapesBack){
            switch(tempType.concat(shape.type)){
              case "machinemachine":

                if(canvasGlobal.isPointInPath( machineArea.get(shape.shapeID),e.offsetX,e.offsetY)){

                  createLineFlag = false;
                  createdLine = true;
                  selectLine = false;
                  lineButtonFlag = false;

                  if(draw_line != null && (draw_line.width != 0 && draw_line.height != 0)){
                    this.placeElement(draw_line, "");
                    var func = prompt("Enter a Transfer Function");
                    draw_line.func = func;
                    lineFuncs.set(draw_line.shapeID, func);
                    shapesBack.push(draw_line);
                  }

                  if(forwardProductionNetwork.has(fromElement)){
                    forwardProductionNetwork.get(fromElement).push(func+" "+shape.shapeID)

                  }else{
                    forwardProductionNetwork.set(fromElement, [func+" "+shape.shapeID])

                  }

                  draw_line = null;
                  document.getElementById("line")!.style.backgroundColor = "transparent";
                  console.log(fromElement);


                  fromElement = null;
                  console.log(forwardProductionNetwork);

                }
                break;


                default :
                  canvasGlobal.clearRect(0,0,1380,675);
                  createLineFlag =false;
                  createdLine = true;
                  selectLine = false;
                  lineButtonFlag = false;
                  document.getElementById("line")!.style.backgroundColor = "transparent"
                  for(var i = 0; i < shapesBack.length; i++){
                    this.placeElement(shapesBack[i], "");
                  }
                  break;


              }
            }

          }
          draw_line = null;

          canvasGlobal.clearRect(0,0,1380,675);
          for(var i = 0; i < shapesBack.length; i++){
            this.placeElement(shapesBack[i], "");
          }

        tempType = "";


      });

      if(createLineFlag){
        document.getElementById("line")!.style.backgroundColor = "rgba(58, 57, 57, 0.856)"

      }

    }


//----------------------------------------------------------------------//

  createMachine(){
    createLineFlag = false;
    createQueueFlag = false;

    createdLine = false;
    createdQueue = false;


    createMachineFlag = true;
    createdMachine = false;


    var boardGlobal = (<HTMLCanvasElement>document.getElementById("board"));
    var canvasGlobal = boardGlobal.getContext("2d")!;

    var machine : shapeBack;

    boardGlobal.addEventListener("mousedown", e=> {

      if(!createdMachine && machineButtonFlag){

        machine={
          x : e.offsetX,
          y : e.offsetY,
          width : 50,
          height : 50,
          stCo : "black",
          type : "machine",
          fiCo : "white",
          is_filled : 1,
          stWi : 2,
          shapeID : "Machine"+machineCounter,
          order :machineCounter,
          func: null

          }
        createMachineFlag = false;
        createdMachine = true;
        console.log(machine);
        this.placeElement(machine, "");
        shapesBack.push(machine);
        machineCounter++;
        }

    });


    boardGlobal.addEventListener("mouseup",e=>{
      if(machineButtonFlag){
        createdMachine = true;
        createMachineFlag = false;
        machine = null;

        document.getElementById("machine")!.style.backgroundColor = "transparent"


      }

    });
    if(createMachineFlag){
      document.getElementById("machine")!.style.backgroundColor = "rgba(58, 57, 57, 0.856)"

    }
}

//----------------------------------------------------------------------//


  clearAll(){
    var boardGlobal = (<HTMLCanvasElement>document.getElementById("board"));
    var canvasGlobal = boardGlobal.getContext("2d")!;
    canvasGlobal.clearRect(0,0,1380,675);
    machineArea.clear();
    shapesBack = [];
    forwardProductionNetwork.clear();
    backwardProductionNetwork.clear();
    machineCounter = 0;
    queueCounter = 0;
    clearInterval(this.playEvent)
    this.ngOnInit();
    for(var i = 0; i < shapesBack.length; i++){
      this.placeElement(shapesBack[i], "");
    }
  }

//----------------------------------------------------------------------//


run(){
  var playFlag = true;


  var boardGlobal = (<HTMLCanvasElement>document.getElementById("board"));
  var canvasGlobal = boardGlobal.getContext("2d")!;

  console.log(forwardProductionNetwork);

  console.log(forwardProductionNetwork.size)
  var temp : string[][][][] = [];

  temp.push([]);
  temp.push([]);

  let ctr = 0;

  forwardProductionNetwork.forEach((key, value) => {

    temp[0].push([]);
    temp[0][ctr].push([]);
    temp[0][ctr].push([]);
    temp[0][ctr][1] = key;
    temp[0][ctr][0].push(value);
    ctr++;
    console.log("Key ", key);
    console.log("value ", value);

  })

  if(forwardProductionNetwork.size == 0 ){
    playFlag = false;
  }

  const convMap = Object.create(null);

  if(playFlag){
    forwardProductionNetwork.forEach((val: string[], key: string) => {
      convMap[key] = val;
    });
  }


  if(playFlag){
    this.server.generateNetwork(JSON.stringify(convMap)).subscribe((data)=>{
          this.server.play().subscribe((data)=>{
            this.result.data = [data];
            this.router.navigate(['/result']);
          });
    });
  }
  else{
    console.log("NETWORK IS NOT COMPLETE");
    alert("EACH MACHINE SHOULD BE CONNECTED TO ONE QUEUE FROM BOTH SIDES");
  }

}

  stop(){
    this.server.stop().subscribe((data:string)=>{
      console.log(data)
      clearInterval(this.playEvent);
      clearInterval(this.replayEvent);


    });
  }

//----------------------------------------------------------------------//

  disableButtons(){
    if(createLineFlag){

      machineButtonFlag = false;
      queueButtonFlag  = false;
      clearInterval(this.playEvent);
      clearInterval(this.replayEvent);


      lineButtonFlag = true;


    }

    if(createMachineFlag){



      queueButtonFlag = false;
      lineButtonFlag = false;
      machineButtonFlag = true;
      clearInterval(this.playEvent);
      clearInterval(this.replayEvent);
      draw_line = null;
    }
    if(createQueueFlag){

      machineButtonFlag  = false;
      lineButtonFlag = false;
      clearInterval(this.playEvent);
      clearInterval(this.replayEvent);
      queueButtonFlag = true;
      draw_line = null;
    }


    if(!createQueueFlag){
      document.getElementById("queue")!.style.backgroundColor = "transparent"

    }
    if(!createMachineFlag){
      document.getElementById("machine")!.style.backgroundColor = "transparent"

    }
    if(!createLineFlag){
      document.getElementById("line")!.style.backgroundColor = "transparent"

    }


  }

//----------------------------------------------------------------------//


}
