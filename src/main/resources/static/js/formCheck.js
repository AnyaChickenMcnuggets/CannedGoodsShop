function checkNum(){
    let o = parseInt(document.getElementById("ot").value);
    let d = parseInt(document.getElementById("do").value);
    if (o<=0){
        document.getElementById("ot").value = null;
    }
    if (d<=o){
        d= o+1;
        document.getElementById("do").value = d;
    }
    if (d<=0 || d>=100000){
        document.getElementById("do").value = null;
    }
}