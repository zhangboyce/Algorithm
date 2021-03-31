function Vertex() {
    this.vertexes = [];
}

Vertex.prototype.degree = function () {
    return this.vertexes.length;
};

Vertex.prototype.addVertex = function(vertex) {
    if (this.vertexes.indexOf(vertex) === -1) {
        this.vertexes.push(vertex);
    }
};