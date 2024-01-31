(function(exports) {
    
    async function drawPngJpg(arrayBuf, imgSelector) {
        let imageUrl = URL.createObjectURL(new Blob([arrayBuf]));
        let image = new Image();
        image.onload = function() {
            const canvas = document.createElement("canvas");
            const ctx = canvas.getContext("2d");
            canvas.width = image.width;
            canvas.height = image.height;
            ctx.drawImage(image, 0, 0);
            document.querySelector(imgSelector).src = canvas.toDataURL();
        }
        image.src = imageUrl;
    }

    async function drawImage(arrayBuf, contentType, imgSelector) {
        switch(contentType) {
            default:
                return drawPngJpg(arrayBuf, imgSelector);
        }
    }

    async function fetchImage(url, data, imgSelector) {
        const response = await fetch(url, {
            "method": "POST",
            "headers": {
                "Content-Type": "application/json"
            },
            "body": JSON.stringify(data)
        });
        if(response.ok) {
            const contentType = response.headers.get("Content-Type");
            const arrayBuf = await response.arrayBuffer();
            drawImage(arrayBuf, contentType, imgSelector);
        }
    }

    exports.fetchImage = fetchImage;


})((typeof exports === 'undefined' ? this['imageFetch'] = {} : exports));