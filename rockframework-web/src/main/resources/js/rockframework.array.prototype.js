/*
* This file is part of rockframework.
* 
* rockframework is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 3 of the License, or
* (at your option) any later version.
* 
* rockframework is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>;.
*/
Array.prototype.add = function(o) {
    this.push(o);
}

Array.prototype.addAt = function(index, o) {
    if(this.length > parseInt(index)) {
        var tmp = this[parseInt(index)];
        this[index] = o;
        for(var i = parseInt(index) + 1; i < this.length; i++) {
            var aux = this[i];
	        this[i] = tmp;
	        tmp = aux;
        }
        this.push(tmp);
    }
    else {
        this.push(o);
    }
}

Array.prototype.get = function(index) {
    if(this.length < parseInt(index)) {
        return null;
    }
    return this[parseInt(index)];
}

Array.prototype.remove = function(index) {
    if(this.length < parseInt(index)) {
        return null;
    }
    var tmp = null;
    for(var i = parseInt(index); i < this.length - 1; i++) {
        tmp = this[i];
        this[i] = this[i + 1];
        this[i + 1] = tmp;
    }
    this.pop();
    return tmp;
}
