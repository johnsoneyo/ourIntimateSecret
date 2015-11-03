/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
var total = 0.0;

var cartItems = {};

function MyOrder(quantity, amount) {
    this.quantity = quantity;
    this.amount = amount;
}

function listenToQuantity(val) {

    var d = document.getElementById(val).value;
    var k = document.getElementById('p' + val).innerHTML;
    var g = d * k;
    document.getElementById('s' + val).innerHTML = g;
    var ord = new MyOrder(d, k);
    cartItems[val] = ord;

//     total = total + g;

    var eg = 0.0;
    for (var f in cartItems) {
        var c = cartItems[f];
        var eg = eg + c.quantity * c.amount;
    }
    document.getElementById('t').innerHTML = eg;

 

}

function getCurrentTotal(){
    
    var eg = 0.0;
    for (var f in cartItems) {
        var c = cartItems[f];
        var eg = eg + c.quantity * c.amount;
    }
    document.getElementById('t').innerHTML = eg; 
    
}

