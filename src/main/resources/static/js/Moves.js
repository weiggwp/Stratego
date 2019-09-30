let selected = undefined;
let x = undefined;
let y = undefined;
let numMoves=0;
let moving = -1;
let clicked=false;
let yellow=-1;
let yellowBorder=-1;
let started=false;
// let legal=0;
let gameOver=false;
let revealedOne=-1;
let revealedTwo=-1;

let moveList=null;
// let moveCounter=0;
// let testThread=false;
let replaying=false;
let deletedImages=null;
function startReplay() {
    replaying=true;
    console.log("CALLED");
    var http = new XMLHttpRequest();
    let url = "/replay/get_Movelist";    //-> will be changed to another uri maybe action?=move
    //sent json file is 0-based index
    var params = JSON.stringify({});
    //start_x and start_y need to be filled in to validate move

    http.open("POST", url, true);

    http.setRequestHeader("Content-type", "application/json; charset=utf-8");
    // http.setRequestHeader("Content-length", params.length);
    // http.setRequestHeader("Connection", "close");

    http.send();

    //will prob need to separate and make a more sophisticated function

    http.onload = function () {

        if (http.status != 200) { // analyze HTTP status of the response
            alert(`Error ${http.status}: ${http.statusText}`); // e.g. 404: Not Found
        } else {
            // show the result
            moveList=JSON.parse(http.response.toString());
            deletedImages=new Array(moveList.length)
        }

    }
}
function undoMove(){
    if (forward) return;
    document.getElementById("nextMoveBtn").style.visibility='visible';
    if (numMoves==0) return;
    numMoves--;
    performMove(moveList[numMoves].end_x*10+moveList[numMoves].end_y+11,
        moveList[numMoves].start_x*10+moveList[numMoves].start_y+11,
        numMoves%2==0?'B':'R',
        moveList[numMoves].status.fight_result,
        moveList[numMoves].status.image_src,
        false,"",
        true,true);
    if (moveList[numMoves].status.fight_result==0){//win
        document.getElementById((moveList[numMoves].end_x*10+moveList[numMoves].end_y+11).toString()).src=deletedImages[numMoves];
        document.getElementById((moveList[numMoves].end_x*10+moveList[numMoves].end_y+11).toString()).style.opacity='1';
        document.getElementById((moveList[numMoves].start_x*10+moveList[numMoves].start_y+11).toString()).style.opacity='1';
    }
    else if (moveList[numMoves].status.fight_result==1){//lose
        /*if (numMoves%2==1)
        document.getElementById((moveList[numMoves].end_x*10+moveList[numMoves].end_y+11).toString()).src=deletedImages[numMoves];*/

        document.getElementById((moveList[numMoves].start_x*10+moveList[numMoves].start_y+11).toString()).style.opacity='1';
        document.getElementById((moveList[numMoves].end_x*10+moveList[numMoves].end_y+11).toString()).style.opacity='1';
    }
    else if (moveList[numMoves].status.fight_result==2){//draw
        /*document.getElementById((moveList[numMoves].end_x*10+moveList[numMoves].end_y+11).toString()).src=deletedImages[numMoves][1];
        document.getElementById((moveList[numMoves].end_x*10+moveList[numMoves].end_y+11).toString()).src=deletedImages[numMoves][0];*/
        document.getElementById((moveList[numMoves].start_x*10+moveList[numMoves].start_y+11).toString()).style.opacity='1';
        document.getElementById((moveList[numMoves].end_x*10+moveList[numMoves].end_y+11).toString()).style.opacity='1';
    }
    document.getElementById("restartBtn").style.visibility='hidden';
    document.getElementById("restartText").style.visibility='hidden';
    //numMoves++;
    if (numMoves==0)   document.getElementById("undoMoveBtn").style.visibility='hidden';
}
let forward=false;
function fastForwardReplay(time){
    forward=!forward;
    if (!forward){
        console.log("Forward off.");
        document.getElementById('fastForwardReplayBtn').innerHTML='Fast Forward';
        document.getElementById('nextMoveBtn').style.opacity='1';
        document.getElementById('undoMoveBtn').style.opacity='1';
        return;
    }
    if (numMoves==moveList.length) return;
    console.log("looping, num moves is " + numMoves+ " len is " +moveList.length+" time is "+time);
    setTimeout(function(){nextMove(); fastForwardReplayAuto(750);}, time);



}
function fastForwardReplayAuto(time){
    if (!forward){
        document.getElementById('fastForwardReplayBtn').innerHTML='Fast Forward';
        document.getElementById('nextMoveBtn').style.opacity='1';
        document.getElementById('undoMoveBtn').style.opacity='1';
        return;
    }
    document.getElementById('nextMoveBtn').style.opacity='.65';
    document.getElementById('undoMoveBtn').style.opacity='.65';
    document.getElementById('fastForwardReplayBtn').innerHTML='Pause';
    console.log("forward is " +forward);
    if (numMoves==moveList.length){
        document.getElementById('fastForwardReplayBtn').innerHTML='Fast Forward';
        document.getElementById('nextMoveBtn').style.opacity='1';
        document.getElementById('undoMoveBtn').style.opacity='1';
        forward=false;
        return;
    }

    console.log("looping, num moves is " + numMoves+ " len is " +moveList.length+" time is "+time);
    setTimeout(function(){nextMove(true); fastForwardReplayAuto(750);}, time);
}
function fastForwardReplayTime(){
    fastForwardReplay(0)
}
function nextMoveClick(){
    if (!forward) nextMove(false);
}
function nextMove(fastForward){
    if (fastForward&&!forward){
        fastForwardReplayTime();
        return;
    }
    if (numMoves==moveList.length) {
        fastForwardReplayTime();
        return;
    }
    document.getElementById("undoMoveBtn").style.visibility='visible';
    console.log(moveList[numMoves]);
    performMove(moveList[numMoves].start_x*10+moveList[numMoves].start_y+11,
        moveList[numMoves].end_x*10+moveList[numMoves].end_y+11,
        numMoves%2==0?'B':'R',
        moveList[numMoves].status.fight_result,
        moveList[numMoves].status.image_src,
        false,
        "",
        true,false);

    numMoves++;
    if (numMoves==moveList.length){
        document.getElementById("nextMoveBtn").style.visibility='hidden';
        // document.getElementById("fastForwardReplayBtn").style.visibility='hidden';
        document.getElementById("restartBtn").style.visibility='visible';
        document.getElementById("restartText").style.visibility='visible';
    }
    console.log("num moves is " + numMoves+ " len is " +moveList.length);
}
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
let lastsrc='';
function updateSidebar(src){
    if(replaying)return;
    let num=src.substring(src.lastIndexOf("piece")+5,src.lastIndexOf(".png"));
    console.log("num is "+num);
    let numString = "captured".concat(num);
    console.log("numstr is "+numString);
    let prevNum=parseInt(document.getElementById(numString).innerHTML.charAt(document.getElementById(numString).innerHTML.length-1));
    console.log("prevnum is "+prevNum);
    let newNum=(prevNum+1).toString();
    let finalRet = document.getElementById(numString).innerHTML.substring(0,document.getElementById(numString).innerHTML.length-1).concat(newNum);
    document.getElementById(numString).innerHTML=finalRet;
    if (lastsrc!='')
        document.getElementById(lastsrc).style.color='white';
    document.getElementById(numString).style.color='red';
    lastsrc=numString;


}
function concede(){
    if (confirm("Are you sure you wish to concede the game? This cannot be undone.")){
        var http = new XMLHttpRequest();
        let url = "/concede";    //-> will be changed to another uri maybe action?=move
        //sent json file is 0-based index
        var params = JSON.stringify({});
        //start_x and start_y need to be filled in to validate move

        http.open("POST", url, true);

        http.setRequestHeader("Content-type", "application/json; charset=utf-8");
        // http.setRequestHeader("Content-length", params.length);
        // http.setRequestHeader("Connection", "close");

        http.send(params);

        lose();
    }

}
function hidePieceNums(){
    if (revealedOne!=-1)
        document.getElementById(revealedOne.toString()).src='../images/pieces/blue_back.png'
    if (revealedTwo!=-1)
        document.getElementById(revealedTwo.toString()).src='../images/pieces/blue_back.png'
    revealedOne=-1;
    revealedTwo=-1;
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


let permission =true;

function fastForward(){
    if (!permission)return;
    document.getElementById('fastForwardBtn').style.opacity='.65';
    permission=false;
    hidePieceNums();
   // aiMove('R');
    aiMove('B');
    // aiMove('R');
    // console.log("AAA");

    permission=true;

}

function start() {

    let http = new XMLHttpRequest();
    let url = "/start_game";
    http.open("POST", url, true);
    http.setRequestHeader("Content-type", "application/json; charset=utf-8");
    http.send("game started")
    http.onload = function() {
        if (clicked) {
            console.log("clicking " +moving);
            if (document.getElementById(moving.toString())!=undefined)
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
        document.getElementById('fastForwardBtn').style.visibility='visible';
        document.getElementById('concedeBtn').style.visibility='visible';
    }
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
    if (gameOver) return;
    if (!permission)return;

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
        permission=false;
        hidePieceNums();
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
function aiMoveTest() {
    var ran = Math.floor(Math.random() * 100) + 11;
    // console.log("from " + ran);
    let to=0;
    let from=0;
    while (true) {
        ran = Math.floor(
            Math.random() * 90) + 11;
        // console.log("from "+ran);
        if (!notLake(ran)||!notLake(ran+10)) continue;
        // console.log("opac is "+document.getElementById((ran).toString()).style.opacity);
        if (isBlue(document.getElementById((ran).toString()).src)
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
function aiMove(color) {
    var http = new XMLHttpRequest();
    let url = color=='R'?"/get_AI":"/get_AIPlayer";
    var params = JSON.stringify(
        {color});
    console.log("color is " + color);
    http.open("POST", url, true);
    http.setRequestHeader("Content-type", "application/json; charset=utf-8");
    http.send();

    http.onload = function() {

        if (http.status != 200) { // analyze HTTP status of the response
            alert(`Error ${http.status}: ${http.statusText}`); // e.g. 404: Not Found
        } else {
            //let move =JSON.parse(http.response.toString());
            let response = JSON.parse(http.response.toString());

            let starting_x=response.start_x;
            let starting_y=response.start_y;
            let ending_x=response.end_x;
            let ending_y=response.end_y;
            let legal = response.status.is_valid_move;

            let color = response.color;
            let fight_result = response.status.fight_result;
            let img_src = response.status.image_src;
            let start = (starting_x + 1) * 10 + starting_y + 1;
            let game_ended = response.status.game_ended;
            let game_result = response.status.game_result;
            let end = (ending_x + 1) * 10 + ending_y + 1;
            if (legal==false) return;

            performMove(start, end, color, fight_result, img_src,game_ended,game_result,false,false);

            // sendMoveRequest(0,starting_x,starting_y,ending_x,ending_y,'R',++numMoves);

        }
    }

    // (moving-1)/10-1,(moving-1)%10,i-1,m-1,'B',numMoves)

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
        'status': undefined});
    //start_x and start_y need to be filled in to validate move

    http.open("POST", url, true);

    http.setRequestHeader("Content-type", "application/json; charset=utf-8");
    // http.setRequestHeader("Content-length", params.length);
    // http.setRequestHeader("Connection", "close");

    http.send(params);

    //don't really need a response here, just check if it returns error


}
function lose(){
    gameOver=true;
    document.getElementById('startText').outerHTML='You lost. Press restart to restart';
    //document.getElementById('startText').style.visibility='visible';
    document.getElementById('restartBtn').style.visibility='visible';
    document.getElementById('fastForwardBtn').style.visibility='hidden';
    document.getElementById('concedeBtn').style.visibility='hidden';
    requestBoard();

}
function win(){
    gameOver=true;
    document.getElementById('startText').outerHTML='You win! Press restart to restart';
    //document.getElementById('startText').style.visibility='visible';
    document.getElementById('restartBtn').style.visibility='visible';
    document.getElementById('fastForwardBtn').style.visibility='hidden';
    document.getElementById('concedeBtn').style.visibility='hidden';
    requestBoard();
}
function draw()
{
    gameOver=true;
    document.getElementById('startText').outerHTML='Draw! Press restart to restart';
    //document.getElementById('startText').style.visibility='visible';
    document.getElementById('restartBtn').style.visibility='visible';
    document.getElementById('fastForwardBtn').style.visibility='hidden';
    document.getElementById('concedeBtn').style.visibility='hidden';
    requestBoard();
}
function restart(){
    location.reload()
}

function revealPieces(board){
    if (yellowBorder!=-1)
        document.getElementById(yellowBorder.toString()).style.borderStyle='none';
    for (let i=0; i<board.length; i++){
        for (let j=0; j<board[0].length; j++){
            let startX=(i+1)*10;
            let startY=j+1;
            console.log(board[i][j].img_src);
            if (isBlue(board[i][j].img_src)){
                if (document.getElementById((startY+startX).toString()).src.endsWith("Moved.png")){
                    document.getElementById((startY+startX).toString()).style.opacity='.02';
                    continue;
                }
                console.log("blue!");
                if (notLake(startX+startY)){
                    console.log("notLake! val is" +(startY+startX).toString());
                    document.getElementById((startY+startX).toString()).src=board[i][j].img_src;
                }
            }
        }
    }
}
function requestBoard(){
    var http = new XMLHttpRequest();
    let url = "/get_board";
    http.open("POST", url, true);
    http.setRequestHeader("Content-type", "application/json; charset=utf-8");
    http.send();
    http.onload = function() {

        if (http.status != 200) { // analyze HTTP status of the response
            alert(`Error ${http.status}: ${http.statusText}`); // e.g. 404: Not Found
        } else {
            let board =JSON.parse(http.response.toString());
            revealPieces(board);
        }
    }
}

function performMove(start,end,color,fight_result,img_src,game_ended,game_result,replay, undo){
    if (gameOver)return;
    console.log("start is " + start+", end is " + end);
    if (color==='B') {
        if (lastsrc!='')
        document.getElementById(lastsrc).style.color='white';

        //if (resp.startsWith("win")) { //it

        if(fight_result===0||fight_result==4) //mover wins
        {
            if (fight_result==0)
                updateSidebar(img_src);
            if (fight_result==0&&replay){
                if (!undo)
                    deletedImages[numMoves]=document.getElementById(end.toString()).src;

            }
            document.getElementById((end).toString()).src
                = document.getElementById((start).toString()).src;
            document.getElementById(start.toString()).style.opacity = ".02";
            document.getElementById(start.toString()).style.borderStyle = 'none';
            document.getElementById((end).toString()).style.opacity = "1";
            moving = -1;
        }
        //if (resp.startsWith("empty")) { //it

        // else if (resp.startsWith("lose ")) { //it
        else if(fight_result===1)
        {
            if (replay){
                if (!undo)
                    deletedImages[numMoves]=document.getElementById(start.toString()).src;
            }
            document.getElementById(start.toString()).style.opacity = ".02";
            document.getElementById(start.toString()).style.borderStyle = 'none';
            if (!replay)
                document.getElementById((end).toString()).src=img_src;
            revealedOne=end;
            console.log(img_src);
            //(resp.substring(resp.lastIndexOf(" ")+1));
            moving = -1;
        }
        else if (fight_result===2){//draw
            if (replay){
                if (!undo)
                    deletedImages[numMoves]=[document.getElementById(start.toString()).src,document.getElementById(end.toString()).src];
            }
            updateSidebar(img_src);
            document.getElementById(start.toString()).style.opacity = ".02";
            document.getElementById(start.toString()).style.borderStyle = 'none';
            document.getElementById(end.toString()).style.opacity = ".02";
            document.getElementById(end.toString()).style.borderStyle = 'none';
            moving=-1;
        }
        else if (fight_result===3){ //game_over, a win
            document.getElementById((end).toString()).src
                = document.getElementById((start).toString()).src
            document.getElementById(start.toString()).style.opacity = ".02";
            document.getElementById(start.toString()).style.borderStyle = 'none';
            document.getElementById(end.toString()).style.opacity = "1";
            moving = -1;
            if (!replay)
                win();
            return;
        }
        else if(game_ended===true)// check for alternate capturing
        {

            if(game_result==="win")
            {
                win();
                return;
            }
            else if(game_result==="lost")
            {
                lose();
                return;
            }
            else
            {
                draw();
                return;
            }
        }


        if (!replay) {
            aiMove('R');
        }
    }
    else{

        //console.log("starting x is " + starting_x + " starting y is " + starting_y + "ending x is " +target_x+ " ending y is " +
        //target_y);

        // console.log("x is " +x);



        //document.getElementById(((target_x + 1) * 10 + target_y + 1).toString()).src = document.getElementById((x).toString()).src
        //document.getElementById(x.toString()).style.opacity = ".02";
        document.getElementById(start.toString()).style.borderStyle = 'none';
        document.getElementById(end.toString()).style.opacity = "1";
        //if (resp.startsWith("empty")) {
        if(fight_result===4)
        {
            document.getElementById(end.toString()).src  =document.getElementById((start).toString()).src
            if (replay)document.getElementById((start).toString()).style.opacity='.02'
        }
        //if (resp.startsWith("win")) {
        if(fight_result===0)
        {
            if (replay){
                console.log("logging "+document.getElementById(start.toString()).src);
                if (!undo)
                    deletedImages[numMoves]=document.getElementById(end.toString()).src;
                document.getElementById(end.toString()).src =
                    document.getElementById(start.toString()).src
            }
            else
                document.getElementById(end.toString()).src =
                    img_src
            revealedTwo=(end);
            if (replay)document.getElementById((start).toString()).style.opacity='.02'
            if (revealedOne==revealedTwo)revealedTwo=-1;
            //(resp.substring(resp.lastIndexOf(" ")+1));
            //document.getElementById((end).toString()).src=(resp.substring(resp.lastIndexOf(" ")+1));
        }
        //else if (resp.startsWith(("lose"))){
        else if(fight_result===1)
        {   if (!replay)
            updateSidebar(img_src);
            if (replay){
                if (!undo)
                    deletedImages[numMoves]=document.getElementById(start.toString()).src;
            }
            if (replay)document.getElementById(start).style.opacity='.02';
        }
        //else if (resp.startsWith("draw")){
        else if(fight_result===2)
        {
            if (replay){
                if (!undo)
                    deletedImages[numMoves]=[document.getElementById(start.toString()).src,document.getElementById(end.toString()).src];
            }
            if (!replay) updateSidebar(img_src);
            document.getElementById(end.toString()).style.opacity='.02';
        }
        //else if (resp.startsWith("flag")){
        else if(fight_result===3)
        {
            if (!replay)
                lose();
        }
        else if(game_ended===true)// check for alternate capturing
        {
            if(game_result==="win")
            {
                lose();
                return;
            }
            else if(game_result==="lost")
            {
                win();
                return;
            }
            else
            {
                draw();
                return;
            }
        }
        if (!replay){
            document.getElementById('fastForwardBtn').style.opacity='1';
            if (yellow!==-1){
                if (document.getElementById(yellow.toString()).src.endsWith('images/pieces/blue_back.png')
                    ||document.getElementById(yellow.toString()).src.endsWith('images/pieces/Moved.png'))
                    if (yellow!=(end))
                        document.getElementById(yellow.toString()).style.opacity=".02";
                console.log("x is " + (end) + " yellow is " + yellow);

                document.getElementById(yellowBorder.toString()).style.borderStyle='none';
                if (document.getElementById(yellowBorder.toString()).src.endsWith("blank.png"))
                    document.getElementById(yellowBorder.toString()).style.opacity='.02';
            }
            yellow=start;
            yellowBorder=(end);
            document.getElementById(yellow.toString()).src='../images/pieces/Moved.png';
            document.getElementById(yellowBorder.toString()).style.borderStyle='solid';
            document.getElementById(yellowBorder.toString()).style.borderWidth='2px';
            document.getElementById(yellowBorder.toString()).style.borderColor='Yellow';
            permission=true;
        }
    }



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
        'status': undefined});
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
            //console.log("RESPONSE IS "+http.response.toString());

            let response = JSON.parse(http.response.toString());
            let legal = response.status.is_valid_move;
            let fight_result = response.status.fight_result;
            let game_ended = response.status.game_ended;
            let game_result = response.status.game_result;
            let img_src = response.status.image_src;
            console.log("imgsrc is " + response.status.image_src);
            let start = (starting_x + 1) * 10 + starting_y + 1;
            let end = (target_x + 1) * 10 + target_y + 1;
            if (legal==false) return;
            performMove(start, end, color, fight_result, img_src,game_ended,game_result,false,false);
           // start,end,color,fight_result,game_ended,game_result,img_src,replay, undo
            //http.response.toString();


        }
    }
}




