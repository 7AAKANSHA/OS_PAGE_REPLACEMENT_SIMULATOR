function runclock() {
    const refString = document.getElementById("pagesInput").value.trim();
    const frameSize = parseInt(document.getElementById("frameSizeInput").value);

    if (!refString || isNaN(frameSize)) {
        alert("Please enter a valid reference string and frame size.");
        return;
    }

    const pages = refString.split(" ").map(Number);

    fetch("/api/clock/run", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            pages: pages,
            frameSize: frameSize
        })
    })
        .then(response => response.json())
        .then(data => {
            displayclockResult(data);
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Something went wrong. Check the console.");
        });
}

function displayclockResult(data) {
    const resultDiv = document.getElementById("resultSection");

    let html = `<h3>Step-by-Step Output</h3>`;
    html += `<table><tr><th>Step</th><th>Page</th><th>Frame State</th><th>Result</th></tr>`;

    data.steps.forEach(step => {
        const frameState = step.frameState ? step.frameState.join(", ") : "N/A";
        const result = step.hit ? "Page Hit ✅" : "Page Fault ❌";
        const rowClass = step.hit ? "hit" : "fault";

        html += `<tr class="${rowClass} animated-step" style="animation-delay: ${step.step * 0.3}s">
      <td>${step.step}</td>
      <td>${step.page}</td>
      <td>${frameState}</td>
      <td>${result}</td>
    </tr>`;
    });

    html += `</table><br>`;
    html += `<p><strong>Total Hits:</strong> ${data.totalHits}</p>`;
    html += `<p><strong>Total Faults:</strong> ${data.totalFaults}</p>`;
    html += `<p><strong>Page Hit Ratio:</strong> ${data.hitRatio}%</p>`;
    html += `<p><strong>Page Fault Ratio:</strong> ${data.faultRatio}%</p>`;
    resultDiv.innerHTML = html;
}
