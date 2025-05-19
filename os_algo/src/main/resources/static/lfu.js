function runlfu() {
    const refInput = document.getElementById("pagesInput");
    const frameInput = document.getElementById("frameSizeInput");

    const refString = refInput.value.trim();
    const frameSize = parseInt(frameInput.value);
    if (!pagesInput || isNaN(frameSize)) {
        alert("Please enter a valid reference string and frame size.");
        return;
    }

    fetch("/api/lfu/run", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            referenceString: refString,
            frames: frameSize
        })
    })
        .then(response => response.json())
        .then(data => {
            console.log("🔍 LFU Response:", data);
            displayLfuResult(data);
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Something went wrong. Check the console.");
        });
}

function displayLfuResult(data) {
    const resultDiv = document.getElementById("resultSection");

    if (!data || !data.steps) {
        resultDiv.innerHTML = "<p style='color:red;'>No steps returned. Please check input or backend.</p>";
        return;
    }

    let html = `<h3>Step-by-Step Output</h3>`;
    html += `<table><tr><th>Step</th><th>Page</th><th>Frame State</th><th>Frequencies</th><th>Result</th></tr>`;

    data.steps.forEach(step => {
        const frameState = step.frames ? step.frames.join(", ") : "N/A";
        const freq = step.frequencies ? JSON.stringify(step.frequencies) : "N/A";
        const result = step.hit ? "Page Hit ✅" : "Page Fault ❌";
        const rowClass = step.hit ? "hit" : "fault";

        html += `<tr class="${rowClass} animated-step" style="animation-delay: ${step.step * 0.3}s">
          <td>${step.step}</td>
          <td>${step.page}</td>
          <td>${frameState}</td>
          <td>${freq}</td>
          <td>${result}</td>
        </tr>`;
    });

    html += `</table><br>`;
    html += `<p><strong>Total Hits:</strong> ${data.pageHits ?? 0}</p>`;
    html += `<p><strong>Total Faults:</strong> ${data.pageFaults ?? 0}</p>`;
    html += `<p><strong>Page Hit Ratio:</strong> ${data.hitRatio}%</p>`;
    html += `<p><strong>Page Fault Ratio:</strong> ${data.faultRatio}%</p>`;
    resultDiv.innerHTML = html;
}
