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
//String
String.prototype.equals = function(str) {
    return this == str;
}

String.prototype.equalsIgnoreCase = function(str) {
    return this.toUpperCase() == str.toUpperCase();
}

String.prototype.lpad = function(c, size) {
    var x = this;
    while(x.length < size) {
        x = c + x;
    }
    return x;
}

String.prototype.rpad = function(c, size) {
    var x = this;
    while(x.length < size) {
        x += c;
    }
    return x;
}

String.prototype.replaceAll = function(oldStr, newStr) {
    return this.replace(new RegExp(oldStr, "gim"), newStr)
}

String.prototype.trim = function() {
    return this.replace(/^\s+|\s+$/, "");
}