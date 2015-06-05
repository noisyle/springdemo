EnemyTank = function (index, game, player, startX, startY) {

	var x = startX;
    var y = startY;
    this.lastPos = {x:x,y:y};

    this.game = game;
    this.health = 3;
    this.player = player;
    this.alive = true;

    this.shadow = game.add.sprite(x, y, 'enemy', 'shadow');
    this.tank = game.add.sprite(x, y, 'enemy', 'tank1');
    this.turret = game.add.sprite(x, y, 'enemy', 'turret');

    this.shadow.anchor.set(0.5);
    this.tank.anchor.set(0.5);
    this.turret.anchor.set(0.3, 0.5);

    this.tank.name = index.toString();
    game.physics.enable(this.tank, Phaser.Physics.ARCADE);
    this.tank.body.immovable = false;
    this.tank.body.collideWorldBounds = true;
    this.tank.body.bounce.setTo(1, 1);

    this.tank.angle = game.rnd.angle();

    game.physics.arcade.velocityFromRotation(this.tank.rotation, 100, this.tank.body.velocity);

};

EnemyTank.prototype.update = function() {
	if(this.tank.x != this.lastPos.x || this.tank.y != this.lastPos.y) {
        this.tank.rotation = Math.PI + game.physics.angleToXY(this.tank, this.lastPos.x, this.lastPos.y);
    }

    this.shadow.x = this.tank.x;
    this.shadow.y = this.tank.y;
    this.shadow.rotation = this.tank.rotation;

    this.turret.x = this.tank.x;
    this.turret.y = this.tank.y;
    this.turret.rotation = this.game.physics.arcade.angleBetween(this.tank, this.player);
};


function preload () {
    game.load.atlas('tank', contextPath+'/static/site/image/tanks/tanks.png', contextPath+'/static/site/image/tanks/tanks.json');
    game.load.atlas('enemy', contextPath+'/static/site/image/tanks/enemy-tanks.png', contextPath+'/static/site/image/tanks/tanks.json');
    game.load.image('logo', contextPath+'/static/site/image/tanks/logo.png');
    game.load.image('bullet', contextPath+'/static/site/image/tanks/bullet.png');
    game.load.image('earth', contextPath+'/static/site/image/tanks/scorched_earth.png');
    game.load.spritesheet('kaboom', contextPath+'/static/site/image/tanks/explosion.png', 64, 64, 23);
}

var cometd = $.cometd;
cometd.configure({
    url: location.protocol + "//" + location.host + contextPath + "/cometd"
});
cometd.addListener('/meta/handshake', _metaHandshake);
cometd.handshake();

var game;
var land;

var shadow;
var tank;
var turret;

var enemies;

var currentSpeed = 0;
var cursors;

function create () {

    //  Resize our game world to be a 2000 x 2000 square
    game.world.setBounds(-500, -500, 1500, 1500);

    //  Our tiled scrolling background
    land = game.add.tileSprite(0, 0, 800, 600, 'earth');
    land.fixedToCamera = true;

    //  The base of our tank
    var x = game.world.randomX;
    var y = game.world.randomY;
    tank = game.add.sprite(x, y, 'tank', 'tank1');
    tank.anchor.setTo(0.5, 0.5);
    tank.animations.add('move', ['tank1', 'tank2', 'tank3', 'tank4', 'tank5', 'tank6'], 20, true);

    //  This will force it to decelerate and limit its speed
    game.physics.enable(tank, Phaser.Physics.ARCADE);
    tank.body.drag.set(0.2);
    tank.body.maxVelocity.setTo(400, 400);
    tank.body.collideWorldBounds = true;

    //  Finally the turret that we place on-top of the tank body
    turret = game.add.sprite(0, 0, 'tank', 'turret');
    turret.anchor.setTo(0.3, 0.5);

    //  Create some baddies to waste :)
    enemies = [];

    //  A shadow below our tank
    shadow = game.add.sprite(0, 0, 'tank', 'shadow');
    shadow.anchor.setTo(0.5, 0.5);

    tank.bringToTop();
    turret.bringToTop();

    game.camera.follow(tank);
    game.camera.deadzone = new Phaser.Rectangle(150, 150, 500, 300);
    game.camera.focusOnXY(0, 0);

    cursors = game.input.keyboard.createCursorKeys();
    
	cometd.batch(function(){
        // Publish on a service channel since the message is for the server only
    	onSocketConnected();
//        cometd.subscribe('/tanks/newplayer', onNewPlayer);
//        cometd.subscribe('/tanks/moveplayer', onMovePlayer);
//        cometd.subscribe('/tanks/removeplayer', onRemovePlayer);
    });

}
// Function invoked when first contacting the server and
// when the server has lost the state of this client
function _metaHandshake(handshake)
{
    if (handshake.successful === true)
    {
    	game = new Phaser.Game(800, 600, Phaser.AUTO, 'game', { preload: preload, create: create, update: update, render: render });
    }
}
// Socket connected
function onSocketConnected() {
    console.log("Connected to socket server");
    // Send local player data to the game server
    cometd.publish('/tanks/newplayer', {x: tank.x, y:tank.y});
};
// Socket disconnected
function onSocketDisconnect() {
    console.log("Disconnected from socket server");
};
// New player
function onNewPlayer(data) {
    console.log("New player connected: "+data.id);
    // Add new player to the remote players array
    enemies.push(new EnemyTank(data.id, game, tank, data.x, data.y));
};
// Move player
function onMovePlayer(data) {
    var movePlayer = playerById(data.id);
    // Player not found
    if (!movePlayer) {
        console.log("Player not found: "+data.id);
        return;
    };
    // Update player position
    movePlayer.tank.x = data.x;
    movePlayer.tank.y = data.y;
};
// Remove player
function onRemovePlayer(data) {
    var removePlayer = playerById(data.id);
    // Player not found
    if (!removePlayer) {
        console.log("Player not found: "+data.id);
        return;
    };
    removePlayer.shadow.kill();
    removePlayer.tank.kill();
    removePlayer.turret.kill();
    // Remove player from array
    enemies.splice(enemies.indexOf(removePlayer), 1);
};
//Find player by ID
function playerById(id) {
    var i;
    for (i = 0; i < enemies.length; i++) {
        if (enemies[i].tank.name == id)
            return enemies[i];
    };
    return false;
};

function update () {
	for (var i = 0; i < enemies.length; i++)
    {
        if (enemies[i].alive)
        {
            enemies[i].update();
            game.physics.collide(tank, enemies[i].player);
        }
    }
    if (cursors.left.isDown)
    {
        tank.angle -= 4;
    }
    else if (cursors.right.isDown)
    {
        tank.angle += 4;
    }

    if (cursors.up.isDown)
    {
        //  The speed we'll travel at
        currentSpeed = 300;
    }
    else
    {
        if (currentSpeed > 0)
        {
            currentSpeed -= 4;
        }
    }

    if (currentSpeed > 0)
    {
        game.physics.arcade.velocityFromRotation(tank.rotation, currentSpeed, tank.body.velocity);
    }

    land.tilePosition.x = -game.camera.x;
    land.tilePosition.y = -game.camera.y;

    //  Position all the parts and align rotations
    shadow.x = tank.x;
    shadow.y = tank.y;
    shadow.rotation = tank.rotation;

    turret.x = tank.x;
    turret.y = tank.y;

    turret.rotation = game.physics.arcade.angleToPointer(turret);
    
    cometd.publish('/tanks/moveplayer', {x: tank.x, y:tank.y});
}

function render () {

}

$(window).unload(function(){
    cometd.disconnect(true);
});
