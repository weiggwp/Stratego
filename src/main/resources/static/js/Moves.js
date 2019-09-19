let selected = undefined;
let numMoves=0;
let moving = -1;
let clicked=false;
yellow=-1;
yellowBorder=-1;
let started=false;
let legal=0;
function highlight(id) {


    let cur_image = document.getElementById(id);
    let dimensions = cur_image.getBoundingClientRect();
    console.log(dimensions);
    //current_image.classList.remove("highlighted");
    if(selected!==undefined && selected!==cur_image)
    {
        selected.classList.remove("highlighted");
    }

    cur_image.classList.toggle("highlighted");

    if(selected===cur_image)
    {
        selected=undefined;
    }
    else{
        selected=cur_image;
    }
    console.log(selected);
    //target.className = (target.className === "red_front") ? "highlighted" : "red_front";
}
function start() {
    if (clicked) {
    console.log("clicking " +moving);
    document.getElementById(moving.toString()).click();
}
    numMoves=0;
    started=true;
    document.getElementsByClassName('blank').draggable = false;
    document.getElementById('startBtn').style.visibility="hidden";
    document.getElementById('startText').style.visibility="hidden";
}
function swap(i,m){
    if (document.getElementById((i*10+m).toString()).src.endsWith("/images/pieces/blue_back.png")) return;
    else console.log(document.getElementById((i*10+m).toString()).src);
    if (moving==-1){

        moving=(i*10+m);
        highlight(moving);

        //alert(moving);
        return;
    }

    if (moving>=0) {
        if (moving==i*10+m){
            highlight(moving)
            moving=-1;
            return;
        }
        //  alert("moving from " + moving + " to " +(i*10+m));
        let tempSrc=document.getElementById((i*10+m).toString()).src;
        document.getElementById((i*10+m).toString()).src=document.getElementById(moving.toString()).src
        document.getElementById(moving.toString()).src=tempSrc;
        highlight(moving);
        moving=-1
    }
}
function move(i,m) {
    clicked=!clicked;
    if (!started){

        swap(i,m);
        return;
    }
    if (moving==-1){
        if (document.getElementById((i*10+m).toString()).src.endsWith("/images/pieces/blue_back.png")) return;
        else console.log(document.getElementById((i*10+m).toString()).src);

        moving=(i*10+m);
        highlight(moving);

        //alert(moving);
        return;
    }

    if (moving>=0) {
        if (moving==i*10+m){
            highlight(moving)
            moving=-1;
            return;
        }
        numMoves++;
        let response=sendMoveRequest(0,(moving-1)/10-1,(moving-1)%10,i-1,m-1,'B',numMoves);


    }
}
function notLake(a){
    if (a!=53&&a!=54&&a!=63&&a!=64&&a!=57&&a!=58&&a!=67&&a!=68)
        return true;
    return false;
}
function getPiece(location){
    return '../images/pieces/piece12.png';
}
function aiMove() {
    var ran = Math.floor(Math.random() * 100) + 11;
   // console.log("from " + ran);
    while (true) {
        ran = Math.floor(Math.random() * 100) + 11;
       // console.log("from "+ran);
        if (!notLake(ran)) continue;
       // console.log("opac is "+document.getElementById((ran).toString()).style.opacity);
        if (document.getElementById((ran).toString()).src.endsWith("/images/pieces/blue_back.png")
            && document.getElementById((ran).toString()).style.opacity != .02)
            break;


    }
    //console.log("from " + ran);
    var from = ran;
    ran = Math.floor(Math.random() * 100) + 11;
   // console.log("to " + ran);
    while (true) {
        ran = Math.floor(Math.random() * 100) + 11;
         console.log("to "+ran);

        if (!notLake(ran)) continue;
        console.log(document.getElementById((ran).toString()).src);
       // console.log("opac is "+document.getElementById((ran).toString()).style.opacity);
        if (!(document.getElementById((ran).toString()).src.endsWith("/images/pieces/blue_back.png"))
            && document.getElementById((ran).toString()).style.opacity != .02)
            break;
        if ((document.getElementById((ran).toString()).src.endsWith("/images/pieces/blue_back.png"))
            && document.getElementById((ran).toString()).style.opacity == .02){
            break;
        }


    }
    //console.log("to " + ran);
    var to = ran;
   // (moving-1)/10-1,(moving-1)%10,i-1,m-1,'B',numMoves)
    sendMoveRequest(0,(from-1)/10-1,(from-1)%10,(to-1)/10-1,(to-1)%10,'R',++numMoves)
    yellow=from;
    yellowBorder=to;

}
function angleBetween(x1,x2, y1,y2){
    slope1=0;
    slope2=(y2-y1)/(x2-x1);
    return Math.atan((slope1+slope2)/(1+slope1*slope2));
}


function sendMoveRequest(GameID,starting_x,starting_y,target_x,target_y,color,moveNum)
{

    var http = new XMLHttpRequest();
    var url = "/post_greet";    //-> will be changed to another uri maybe action?=move
    var params = JSON.stringify({
        GameID: GameID,
        player: "user",
        color: color,
        moveNum:moveNum,
        start_x: starting_x,
        start_y: starting_y,
        end_x: target_x,
        end_y: target_y,
        status: undefined});
    //TODO: this method needs to be moved to Moves.js since we need prev_move coordinates
    //start_x and start_y need to be filled in to validate move

    http.open("POST", url, true);

    http.setRequestHeader("Content-type", "application/json; charset=utf-8");
    http.setRequestHeader("Content-length", params.length);
    http.setRequestHeader("Connection", "close");

    http.send(params);

    //will prob need to separate and make a more sophisticated function
    http.onload = function() {
        if (http.status != 200) { // analyze HTTP status of the response
            alert(`Error ${http.status}: ${http.statusText}`); // e.g. 404: Not Found
        } else { // show the result
            //alert(`Done, got ${http.response.length} bytes`); // responseText is the server
           // alert(http.response.toString());
            console.log(http.response.toString());
            resp = http.response.toString();

                if (color=='B') {
                    let start=(starting_x + 1) * 10 + starting_y + 1;
                    const end = (target_x + 1) * 10 + target_y + 1;
                    console.log("moving from " + moving + " to " + (target_x + 1 * 10 + target_y + 1));
                    if (resp.startsWith("win")) { //it
                        document.getElementById((end).toString()).src
                            = document.getElementById((moving).toString()).src
                        document.getElementById(moving.toString()).style.opacity = ".02";
                        document.getElementById(moving.toString()).style.borderStyle = 'none';
                        document.getElementById(((target_x + 1) * 10 + target_y + 1).toString()).style.opacity = "1";
                        moving = -1;
                    }
                    if (resp.startsWith("empty")) { //it
                        document.getElementById((end).toString()).src
                            = document.getElementById((moving   ).toString()).src
                        document.getElementById(moving.toString()).style.opacity = ".02";
                        document.getElementById(moving.toString()).style.borderStyle = 'none';
                        document.getElementById(((target_x + 1) * 10 + target_y + 1).toString()).style.opacity = "1";
                        moving = -1;
                    }
                    else if (resp.startsWith("lose ")) { //it
                        document.getElementById(moving.toString()).style.opacity = ".02";
                        document.getElementById(moving.toString()).style.borderStyle = 'none';
                        document.getElementById((end).toString()).src=getPiece("");
                        moving = -1;
                    }
                    else if (resp.startsWith("draw")){
                        document.getElementById(moving.toString()).style.opacity = ".02";
                        document.getElementById(moving.toString()).style.borderStyle = 'none';
                        document.getElementById(end.toString()).style.opacity = ".02";
                        document.getElementById(end.toString()).style.borderStyle = 'none';
                    }
                    else if (resp=="flag"){
                        //game over
                    }
                    else if (resp=="illegal")return;

                    aiMove();
                }
                else{


                    let x =((starting_x + 1) * 10 + starting_y + 1)





                    console.log("moving from " + moving + " to " + (target_x + 1 * 10 + target_y + 1));
                    document.getElementById(((target_x + 1) * 10 + target_y + 1).toString()).src = document.getElementById((moving).toString()).src
                    document.getElementById(x.toString()).style.opacity = ".02";
                    document.getElementById(x.toString()).style.borderStyle = 'none';
                    document.getElementById(((target_x + 1) * 10 + target_y + 1).toString()).style.opacity = "1";
                    if (resp==0||true) {
                        let piece = getPiece(x);
                        document.getElementById(((target_x + 1) * 10 + target_y + 1).toString()).src = piece;
                    }
                    if (yellow!=-1){
                        document.getElementById(yellow.toString()).style.opacity=".02";
                        document.getElementById(yellowBorder.toString()).style.borderStyle='none';
                        document.getElementById(yellowBorder.toString()).src='../images/pieces/blue_back.png';
                    }
                }


            }


    };



}