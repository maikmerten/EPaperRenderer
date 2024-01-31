(function(exports) {

    async function uploadObjAsJson(url, obj) {
        const response = await fetch(url, {
            "method": "POST",
            "headers": {
                "Content-Type": "application/json"
            },
            "body": JSON.stringify(obj)
        });
        if(response.ok) {
            return "ok"
        } else {
            throw new Error("uploadObjAsJson, response was not ok")
        }
    }

    async function fetchJson(url) {
        let response = await fetch(url,
            { 
                method: 'GET',
                headers: { 'Accept': 'application/json' }
            }
        );
        return await response.json();
    }

    exports.uploadObjAsJson = uploadObjAsJson;
    exports.fetchJson = fetchJson;

})((typeof exports === 'undefined' ? this['jsonHelper'] = {} : exports));