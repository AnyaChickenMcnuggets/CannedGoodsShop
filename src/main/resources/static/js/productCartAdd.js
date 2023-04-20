function minus(){
    let v = parseFloat(document.getElementById("quantity").value);
    let p = parseFloat(document.getElementById("price").value);
    p = p/v;
    if (v>1){
        v-=1;
        document.getElementById("quantity").value = v;
    }
    p*=v;
    document.getElementById("price").value = p;
}
function plus(){
    let v = parseFloat(document.getElementById("quantity").value);
    let p = parseFloat(document.getElementById("price").value);
    p = p/v;
    v+=1;
    document.getElementById("quantity").value = v;
    p*=v;
    document.getElementById("price").value = p;
}