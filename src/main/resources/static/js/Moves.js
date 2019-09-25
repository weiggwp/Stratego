let selected = undefined;
let x = undefined;
let y = undefined;
let numMoves=0;
let moving = -1;
let clicked=false;
let yellow=-1;
let yellowBorder=-1;
let started=false;
let legal=0;
function highlight(i,m) {
    let id = i*10+m;
    let cur_image = document.getElementById(id.toString());

    //current_image.classList.remove("highlighted");
    if(selected!==undefined && selected!==cur_image)
    {
        selected.classList.remove("highlighted");
    }

    cur_image.classList.toggle("highlighted");

    if(selected===cur_image)
    {
        selected=undefined;
        moving = -1;
        clear_coordinates();

    }
    else{
        selected=cur_image;
        set_coordinates(i,m);

    }
    console.log(selected);
    //target.className = (target.className === "red_front") ? "highlighted" : "red_front";
}
function clear_coordinates()
{
    x = undefined;
    y = undefined;
}
function set_coordinates(i,m)
{
    x = i;
    y = m;
}

function start() {
    if (clicked) {
    // console.log("clicking " +moving);
    document.getElementById(moving.toString()).click();
 }
    numMoves=0;
    started=true;
    document.getElementsByClassName('blank').draggable = false;
    let i=0;
    for (i=11; i<111; i++){
        if (notLake(i))
        document.getElementById(i.toString()).draggable=false;
    }
    document.getElementsByClassName('blank').draggable = false;
    document.getElementById('startBtn').style.visibility="hidden";
    document.getElementById('startText').style.visibility="hidden";
}
function swap(i,m){
    console.log(document.getElementById((i*10+m).toString()).src);
    if (document.getElementById((i*10+m).toString()).src.endsWith("/images/pieces/blue_back.png")) return;
    if (document.getElementById((i*10+m).toString()).style.opacity=='.02') return;
    else console.log(document.getElementById((i*10+m).toString()).src);
    if (moving==-1){

        moving=(i*10+m);
        highlight(i,m);

        //alert(moving);
        return;
    }

    if (moving>=0) {
        if (moving==i*10+m){
            highlight(i,m);
            moving=-1;
            return;
        }
        //  alert("moving from " + moving + " to " +(i*10+m));
        let tempSrc=document.getElementById((i*10+m).toString()).src;
        document.getElementById((i*10+m).toString()).src=document.getElementById(moving.toString()).src
        document.getElementById(moving.toString()).src=tempSrc;
        sendSwapRequest(0,x,y,i,m,'B');
        highlight(Math.floor(moving/10),moving%10);
        moving=-1;
        console.log("swapping from " + x + y+ " to " + i + m);
        // clear_coordinates();
    }
}
function isImmovable( s){
    if (isBlue(s)) return true;
    if (s.endsWith("/images/pieces/piece71.png") )return true;
    if (s.endsWith("/images/pieces/piece21.png") )return true;
    if (s.endsWith("/images/pieces/Moved.png") )return true;
    if (s.endsWith("/images/pieces/blank.png") )return true;
    return false;
}
function isBlue(s){
    if (s.endsWith("/images/pieces/blue_back.png"))return true;
    if (s.endsWith("/images/pieces/piece11.png") )return true;
    if (s.endsWith("/images/pieces/piece12.png") )return true;
    if (s.endsWith("/images/pieces/piece13.png") )return true;
    if (s.endsWith("/images/pieces/piece14.png") )return true;
    if (s.endsWith("/images/pieces/piece15.png") )return true;
    if (s.endsWith("/images/pieces/piece16.png") )return true;
    if (s.endsWith("/images/pieces/piece17.png") )return true;
    if (s.endsWith("/images/pieces/piece18.png") )return true;
    if (s.endsWith("/images/pieces/piece1.png") )return true;
    if (s.endsWith("/images/pieces/piece2.png") )return true;
    if (s.endsWith("/images/pieces/piece3.png") )return true;
    if (s.endsWith("/images/pieces/piece4.png") )return true;
    return false;
}
function move(i,m) {
    clicked=!clicked;
    if (!started){

        swap(i,m);
        return;
    }
    if (moving==-1){
        if (isImmovable(document.getElementById((i*10+m).toString()).src)) return;
        if (document.getElementById((i*10+m).toString()).style.opacity=='.02'||document.getElementById((i*10+m).toString()).style.opacity==.02) return;
        console.log(document.getElementById((i*10+m).toString()).style.opacity);
        //else console.log(document.getElementById((i*10+m).toString()).src);

        moving=(i*10+m);
        highlight(i,m);

        //alert(moving);
        return;
    }

    if (moving>=0) {
        if (moving==i*10+m){
            highlight(i,m)
            moving=-1;
            return;
        }
        numMoves++;
        let response=sendMoveRequest(0,x-1,y-1,i-1,m-1,'B',numMoves);
        //clear_coordinates();

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
var test=false;
function aiMove() {
    var ran = Math.floor(Math.random() * 100) + 11;
    // console.log("from " + ran);
    let to=0;
    let from=0;
    while (true) {
        ran = Math.floor(Math.random() * 90) + 11;
       // console.log("from "+ran);
        if (!notLake(ran)||!notLake(ran+10)) continue;
        // console.log("opac is "+document.getElementById((ran).toString()).style.opacity);
        if (document.getElementById((ran).toString()).src.endsWith("/images/pieces/blue_back.png")
            &&( document.getElementById((ran).toString()).style.opacity != .02)&&
            (
                document.getElementById((ran+10).toString()).style.opacity == .02||!isBlue(document.getElementById((ran+10).toString()).src))){
            //if (test&&!document.getElementById((ran+10).toString()).src.endsWith("Moved.png"))continue;
            from=ran;
            to=ran+10;
            break;
        }



    }
test=true;


    // (moving-1)/10-1,(moving-1)%10,i-1,m-1,'B',numMoves)
    sendMoveRequest(0,Math.floor((from-1)/10-1),(from-1)%10,Math.floor((to-1)/10-1),(to-1)%10,'R',++numMoves)
    /*  yellow=from;
      yellowBorder=to;*/

}

function sendSwapRequest(GameID,starting_x,starting_y,target_x,target_y,color)
{
    var http = new XMLHttpRequest();
    let url = "/swap_piece";    //-> will be changed to another uri maybe action?=move
    //sent json file is 0-based index
    var params = JSON.stringify({
        'GameID': GameID,
        'player': "user",
        'color': color,
        'moveNum':0,
        'start_x': starting_x,
        'start_y': starting_y,
        'end_x': target_x,
        'end_y': target_y,
        'status': null});
    //start_x and start_y need to be filled in to validate move

    http.open("POST", url, true);

    http.setRequestHeader("Content-type", "application/json; charset=utf-8");
    // http.setRequestHeader("Content-length", params.length);
    // http.setRequestHeader("Connection", "close");

    http.send(params);

    //don't really need a response here, just check if it returns error


}
function lose(){
    document.getElementById('startText').outerHTML='You lost. Press restart to restart';
    //document.getElementById('startText').style.visibility='visible';
    document.getElementById('restartBtn').style.visibility='visible';
}
function win(){
    document.getElementById('startText').outerHTML='You win! Press restart to restart';
    //document.getElementById('startText').style.visibility='visible';
    document.getElementById('restartBtn').style.visibility='visible';
}
function restart(){
    location.reload()
}
function back(){

}
function sendMoveRequest(GameID,starting_x,starting_y,target_x,target_y,color,moveNum)
{

    var http = new XMLHttpRequest();
    let url = "/make_move";    //-> will be changed to another uri maybe action?=move
    //sent json file is 0-based index
    var params = JSON.stringify({
        'GameID': GameID,
        'player': "user",
        'color': color,
        'moveNum':moveNum,
        'start_x': starting_x,
        'start_y': starting_y,
        'end_x': target_x,
        'end_y': target_y,
        'status': null});
    //start_x and start_y need to be filled in to validate move

    http.open("POST", url, true);

    http.setRequestHeader("Content-type", "application/json; charset=utf-8");
   // http.setRequestHeader("Content-length", params.length);
   // http.setRequestHeader("Connection", "close");

    http.send(params);

    //will prob need to separate and make a more sophisticated function

    http.onload = function() {

        if (http.status != 200) { // analyze HTTP status of the response
            alert(`Error ${http.status}: ${http.statusText}`); // e.g. 404: Not Found
        } else { // show the result
            //alert(`Done, got ${http.response.length} bytes`); // responseText is the server
           // alert(http.response.toString());
            console.log("RESPONSE IS "+http.response.toString());

            let response = JSON.parse(http.response.toString());
            let legal = response.user.status.is_valid_move;
            let fight_result = response.user.status.fight_result;

            let game_result = response.user.status.game_ended;
            let img_src = response.user.status.image_src;
            console.log("its " +response.user.status.toString());

                //http.response.toString();

                if (color==='B') {
                    let start=(starting_x + 1) * 10 + starting_y + 1;
                    let end = (target_x + 1) * 10 + target_y + 1;
                    console.log("moving from " + moving + " to " + ((target_x + 1) * 10 + target_y + 1));
                    //if (resp.startsWith("win")) { //it
                    if (legal===false)return;
                    if(fight_result===0) //mover wins
                    {
                        document.getElementById((end).toString()).src
                            = document.getElementById((start).toString()).src;
                        //document.getElementById(moving.toString()).style.opacity = ".02";
                        //document.getElementById(moving.toString()).style.borderStyle = 'none';
                        unhighlight(moving);
                        document.getElementById((end).toString()).style.opacity = "1";
                        moving = -1;
                    }
                    //if (resp.startsWith("empty")) { //it
                    if(fight_result===4)    //4 for no fight,empty
                    {
                        //alert("no fight");
                        document.getElementById((end).toString()).src
                            = document.getElementById((moving   ).toString()).src
                        document.getElementById(end.toString()).style.opacity='1';
                        //document.getElementById(moving.toString()).style.opacity = ".02";
                        //document.getElementById(moving.toString()).style.borderStyle = 'none';

                        unhighlight(moving);
                        document.getElementById(((target_x + 1) * 10 + target_y + 1).toString()).style.opacity = "1";
                        moving = -1;
                    }
                   // else if (resp.startsWith("lose ")) { //it
                    else if(fight_result===1)
                    {

                       // document.getElementById(moving.toString()).style.opacity = ".02";
                        //document.getElementById(moving.toString()).style.borderStyle = 'none';
                        unhighlight(moving);
                        document.getElementById((end).toString()).src=img_src;
                        console.log(img_src);
                            //(resp.substring(resp.lastIndexOf(" ")+1));
                        moving = -1;
                    }
                    else if (fight_result===2){//draw
                        //document.getElementById(moving.toString()).style.opacity = ".02";
                        //document.getElementById(moving.toString()).style.borderStyle = 'none';
                        unhighlight(moving);
                        //document.getElementById(end.toString()).style.opacity = ".02";
                        //document.getElementById(end.toString()).style.borderStyle = 'none';
                        unhighlight(end);
                        moving=-1;
                    }
                    else if (fight_result===3){ //game_over, a win
                        document.getElementById((end).toString()).src
                            = document.getElementById((moving).toString()).src
                        //document.getElementById(moving.toString()).style.opacity = ".02";
                        //document.getElementById(moving.toString()).style.borderStyle = 'none';
                        unhighlight(moving);
                        document.getElementById(((target_x + 1) * 10 + target_y + 1).toString()).style.opacity = "1";
                        moving = -1;
                        win();
                        return;
                    }


                    aiMove();
                }
                else{

                    console.log("starting x is " + starting_x + " starting y is " + starting_y + "ending x is " +target_x+ " ending y is " +
                    target_y);
                    let x =((starting_x + 1) * 10 + starting_y + 1)
                    console.log("x is " +x);


                    console.log("moving from " + x + " to " + ((target_x + 1 )* 10 + target_y + 1));
                    //document.getElementById(((target_x + 1) * 10 + target_y + 1).toString()).src = document.getElementById((x).toString()).src
                    //document.getElementById(x.toString()).style.opacity = ".02";
                    //document.getElementById(x.toString()).style.borderStyle = 'none';
                    //unhighlight(x);
                    document.getElementById(x.toString()).style.borderStyle='none';
                    document.getElementById(((target_x + 1) * 10 + target_y + 1).toString()).style.opacity = "1";
                    //if (resp.startsWith("empty")) {
                    if(fight_result===4)
                    {
                        let piece = '../images/pieces/blue_back.png';
                        document.getElementById(((target_x + 1) * 10 + target_y + 1).toString()).src = piece;
                    }
                    //if (resp.startsWith("win")) {
                    if(fight_result===0)
                    {
                        let piece = '../images/pieces/blue_back.png';
                        document.getElementById(((target_x + 1) * 10 + target_y + 1).toString()).src = img_src;
                            //(resp.substring(resp.lastIndexOf(" ")+1));
                        //document.getElementById((end).toString()).src=(resp.substring(resp.lastIndexOf(" ")+1));
                    }
                    //else if (resp.startsWith(("lose"))){
                    else if(fight_result===1)
                    {
                        //document.getElementById(x).style.opacity='.02';
                    }
                    //else if (resp.startsWith("draw")){
                    else if(fight_result===2)
                    {
                        document.getElementById(((target_x + 1) * 10 + target_y + 1).toString()).src='images/pieces/blank.png'
                    }
                    //else if (resp.startsWith("flag")){
                    else if(fight_result===3)
                    {
                        lose();
                    }
                    if (yellow!==-1){
                        if (document.getElementById(yellow.toString()).src.endsWith('images/pieces/blue_back.png')
                            ||document.getElementById(yellow.toString()).src.endsWith('images/pieces/Moved.png'))
                            if (yellow!=((target_x + 1 )* 10 + target_y + 1))
                            document.getElementById(yellow.toString()).style.opacity=".02";
                            console.log("x is " + ((target_x + 1 )* 10 + target_y + 1) + " yellow is " + yellow);

                        // document.getElementById(yellowBorder.toString()).style.borderStyle='none';
                        // document.getElementById(yellowBorder.toString()).style.borderWidth='0';
                        //
                         // unhighlight(yellowBorder);
                        document.getElementById(yellowBorder.toString()).style.borderStyle='none';
                        document.getElementById(yellowBorder.toString()).style.borderWidth='0';
                        document.getElementById(yellowBorder.toString()).style.borderRadius= "0";

                        if (document.getElementById(yellowBorder.toString()).src.endsWith("blank.png"))
                        document.getElementById(yellowBorder.toString()).style.opacity='.02';
                    }
                    yellow=x;
                    yellowBorder=((target_x + 1) * 10 + target_y + 1);
                    highlight_opponent();


                }


            }
    }
}

function highlight_opponent()
{
    document.getElementById(yellow.toString()).src='../images/pieces/Moved.png';
    document.getElementById(yellowBorder.toString()).style.borderStyle='solid';
    document.getElementById(yellowBorder.toString()).style.borderWidth='2px';
    document.getElementById(yellowBorder.toString()).style.borderColor='yellow';
    document.getElementById(yellowBorder.toString()).style.borderRadius= "12px";
}

function unhighlight(element)
{
    document.getElementById(element.toString()).style.opacity = ".02";
    document.getElementById(element.toString()).style.borderStyle='none';
    document.getElementById(element.toString()).style.borderWidth='0';
    document.getElementById(element.toString()).style.borderRadius= "0";
}




